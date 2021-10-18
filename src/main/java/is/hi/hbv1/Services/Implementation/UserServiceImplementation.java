package is.hi.hbv1.Services.Implementation;

import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private List<User> userRepository = new ArrayList<>();
    private int id_counter;

    @Autowired
    public UserServiceImplementation() {
        // Create 3 random users for our dummy repo. To be removed when JPA added.
        userRepository.add(new User("User1", "user1@net.is", "12345"));
        userRepository.add(new User("User2", "user2@net.is", "hallo"));
        userRepository.add(new User("User3", "user3@net.is", "password"));

        // JPA gives each report and ID, but there we add them manually
        for (User u : userRepository) {
            u.setUserID(id_counter);
            id_counter++;
        }
    }

    @Override
    public User save(User user) {
        user.setUserID(id_counter);
        id_counter++;
        userRepository.add(user);
        return user;
    }

    @Override
    public void delete(User user) {
        userRepository.remove(user);

    }

    @Override
    public User logIn(User user) {
        return null;
    }
}
