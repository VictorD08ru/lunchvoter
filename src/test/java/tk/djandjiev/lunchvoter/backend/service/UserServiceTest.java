package tk.djandjiev.lunchvoter.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import tk.djandjiev.lunchvoter.backend.model.Role;
import tk.djandjiev.lunchvoter.backend.model.User;
import tk.djandjiev.lunchvoter.backend.to.UserTO;
import tk.djandjiev.lunchvoter.backend.util.JpaUtil;
import tk.djandjiev.lunchvoter.backend.util.UserUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.ApplicationException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.*;

class UserServiceTest extends AbstractCachedServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    @Override
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        super.setUp();
    }

    @Test
    void create() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", false, LocalDateTime.now(), Collections.singleton(Role.ROLE_USER));
        User created = userService.create(new User(newUser));
        newUser.setId(created.getId());
        List<User> users = new ArrayList<>(USERS);
        users.add(newUser);
        users.sort(Comparator.comparing(User::getName).thenComparing(User::getEmail));

        assertMatch(userService.getAll(), users);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                userService.create(new User(null, "Duplicate", "user1@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void delete() {
        userService.delete(USER0_ID);
        List<User> users = new ArrayList<>(USERS);
        users.remove(USER0);
        assertMatch(userService.getAll(), users);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(ApplicationException.class, () ->
                userService.delete(1));
    }

    @Test
    void get() {
        User user = userService.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(ApplicationException.class, () ->
                userService.get(1));
    }

    @Test
    void getByEmail() {
        User user = userService.getByEmail("ADMIN@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    void update() {
        User updated = new User(USER0);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        userService.update(new User(updated));
        assertMatch(userService.get(USER0_ID), updated);
    }

    @Test
    void updateWithTo() {
        UserTO updated = UserUtil.getTO(USER0);
        updated.setName("UpdatedName");
        updated.setEmail("kuku@ya.ru");
        userService.update(updated);
        User updatedUser = new User(USER0);
        updatedUser.setName("UpdatedName");
        updatedUser.setEmail("kuku@ya.ru");
        assertMatch(userService.get(USER0_ID), updatedUser);
    }

    @Test
    void getAll() {
        List<User> all = userService.getAll();
        assertMatch(all, USERS);
    }

    @Test
    void testValidation() throws Exception {
        validateRootCause(() -> userService.create(new User(null, "  ", "invalid@yandex.ru", "password",  Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "  ", "password",  Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "invalid@yandex.ru", "",  Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru", "password", true, LocalDateTime.now(), Collections.emptySet())), ConstraintViolationException.class);
    }
}