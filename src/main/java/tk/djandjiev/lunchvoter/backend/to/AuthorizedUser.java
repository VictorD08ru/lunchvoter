package tk.djandjiev.lunchvoter.backend.to;

import tk.djandjiev.lunchvoter.backend.model.User;
import tk.djandjiev.lunchvoter.backend.util.UserUtil;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private UserTO userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(UserTO newTo) {
        userTo = newTo;
    }

    public UserTO getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}