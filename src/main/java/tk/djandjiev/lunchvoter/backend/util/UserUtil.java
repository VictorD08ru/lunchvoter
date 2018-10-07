package tk.djandjiev.lunchvoter.backend.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import tk.djandjiev.lunchvoter.backend.model.Role;
import tk.djandjiev.lunchvoter.backend.model.User;
import tk.djandjiev.lunchvoter.backend.to.UserTO;

public class UserUtil {
    private UserUtil() {}

    public static User createNewFromTO(UserTO newUser) {
        return new User(null, newUser.getName(), newUser.getEmail(), newUser.getPassword(),  Role.ROLE_USER);
    }

    public static UserTO getTO(User user) {
        return new UserTO(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromTO(User user, UserTO userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.isEmpty(password) ? password : passwordEncoder.encode(password));
        user.setEmail(user.getEmail().trim().toLowerCase());
        return user;
    }
}