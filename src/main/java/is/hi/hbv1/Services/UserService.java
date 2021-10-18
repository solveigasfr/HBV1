package is.hi.hbv1.Services;

import is.hi.hbv1.Persistence.Entities.User;

public interface UserService {
    User save(User user);
    void delete(User user);
    User logIn(User user);

}
