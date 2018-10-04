package tk.djandjiev.lunchvoter.backend.service;

import tk.djandjiev.lunchvoter.backend.model.User;
import tk.djandjiev.lunchvoter.backend.to.UserTO;
import tk.djandjiev.lunchvoter.backend.util.exception.ApplicationException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws ApplicationException;

    User get(int id) throws ApplicationException;

    User getByEmail(String email) throws ApplicationException;

    void update(User user);

    void update(UserTO user);

    List<User> getAll();

    void enable(int id, boolean enable);
}