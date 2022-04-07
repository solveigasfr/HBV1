package is.hi.hbv1.Services;

import is.hi.hbv1.Persistence.Entities.User;

import java.util.List;

public interface UserService {
    User save(User user);

    void delete(User user);

    User logIn(User user);

    User findByUserEmail(String userEmail);

    User findByUserID(Long userID);

    List<User> findAll();

    String get_SHA_512(String passwordToHash);

    User changePassword(User user, String password);
}
