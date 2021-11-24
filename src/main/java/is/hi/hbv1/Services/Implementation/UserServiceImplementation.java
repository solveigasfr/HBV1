package is.hi.hbv1.Services.Implementation;

import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Persistence.Repositories.UserRepository;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    /* Ingimar commented out 23.okt
    private List<User> userRepository = new ArrayList<>();
    private int id_counter;
     */
    private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        // Create 3 random users for our dummy repo. To be removed when JPA added.
        /* Ingimar commented out 23.okt
        userRepository.add(new User("User1", "user1@net.is", "12345"));
        userRepository.add(new User("User2", "user2@net.is", "hallo"));
        userRepository.add(new User("User3", "user3@net.is", "password"));

        // JPA gives each report and ID, but there we add them manually
        for (User u : userRepository) {
            u.setUserID(id_counter);
            id_counter++;
        }
         */
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        /* Ingimar commented out 23.okt
        user.setUserID(id_counter);
        id_counter++;
        userRepository.add(user);
        return user;
         */
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }
    @Override
    public User logIn(User user) {
        User doesExist = findByUserName(user.getUserName());
        if(doesExist != null){
            if(doesExist.getUserPassword().equals(user.getUserPassword())){
                return doesExist;
            }
        }
        return null;
    }

    // Hash user password with SHA-512
    @Override
    public String get_SHA_512(String passwordToHash){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // Update user password
    @Override
    public User changePassword(User user, String password) {
        user.setUserPassword(password);
        return userRepository.save(user);
    }
}
