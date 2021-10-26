package is.hi.hbv1.Services;

import is.hi.hbv1.Persistence.Entities.User;

import java.util.List;

public interface UserService {
    User save(User user);
    void delete(User user);
    User logIn(User user);
    User findByUserName(String userName);
    List<User> findAll();

}
