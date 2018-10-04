package tk.djandjiev.lunchvoter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.djandjiev.lunchvoter.backend.to.AuthorizedUser;
import tk.djandjiev.lunchvoter.backend.model.User;
import tk.djandjiev.lunchvoter.backend.repository.UserRepository;
import tk.djandjiev.lunchvoter.backend.to.UserTO;
import tk.djandjiev.lunchvoter.backend.util.exception.ApplicationException;
import tk.djandjiev.lunchvoter.backend.util.exception.NotFoundException;

import java.util.List;

import static tk.djandjiev.lunchvoter.backend.util.UserUtil.prepareToSave;
import static tk.djandjiev.lunchvoter.backend.util.UserUtil.updateFromTo;
import static tk.djandjiev.lunchvoter.backend.util.ValidationUtil.*;


@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void delete(int id) throws ApplicationException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public User get(int id) throws ApplicationException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found entity with id=" + id));
    }

    @Override
    public User getByEmail(String email) throws ApplicationException {
        Assert.notNull(email, "email must not be null");
        return repository.getByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new NotFoundException("Not found user with email=" + email));
    }

    @Cacheable("users")
    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(prepareToSave(user, passwordEncoder)), user.getId());
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    @Override
    public void update(UserTO userTo) {
        User user = updateFromTo(get(userTo.getId()), userTo);
        repository.save(prepareToSave(user, passwordEncoder));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user);  // !! need only for JDBC implementation
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " is not found"));
        return new AuthorizedUser(user);
    }
}