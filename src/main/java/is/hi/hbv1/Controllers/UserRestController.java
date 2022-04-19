package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Persistence.Repositories.UserRepository;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestController {
    private static final Base64.Decoder base64Decoder = Base64.getDecoder(); // for decoding server calls

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/getUser")
    public List<User> getUser() throws InterruptedException {
        // Call a method in a Service Class
        List<User> user = userService.findAll();
        // Add some data to the Model
        //model.addAttribute("reports", allReports);
        return user;
    }

    /* we use loginUser instead of this bs

    @RequestMapping(value = "/validateLogin/{email}/{password}")
    public User validateLogin(@PathVariable(value = "email") String email, @PathVariable(value = "password") String password) {

        if(email == null || password == null) {
            return null;
        }
        System.out.println(email);
        User user = userService.findByUserEmail(email);
        if(!user.getUserPassword().equals(password)) {
            return null;
        }
        User exists = userService.logIn(user);
        if (exists != null) {
            //session.setAttribute("loggedInUser", exists);
            //model.addAttribute("loggedInUser", exists);
            //model.addAttribute("report", report);
            return user;
        }
        //model.addAttribute("emailOrPasswordCondition", true);
        return null;
    }
     */

    @PostMapping(value ="/signupUser")
    public User signupUser(@RequestParam Map<String, String> signupMap) {
        String name = signupMap.get("name");
        String email = signupMap.get("email");
        String passwordToken = passwordDecoder(signupMap.get("token"));

        System.out.println("name" + name);
        System.out.println("email" + email);
        System.out.println("password" + passwordToken);
        User user = new User(name, email, passwordToken);
        userService.save(user);
        user = userService.findByUserEmail(email);
        return userService.logIn(user);
    }

    @RequestMapping("/loginUser/{userToken}")
    public User loginUser(@PathVariable(value = "userToken") String userToken) {
        System.out.println("Encoded string is: " + userToken);
        Pair<String, String> decodedEmailAndPassword = myDecoder(userToken);
        String email = decodedEmailAndPassword.getFirst();
        String password = decodedEmailAndPassword.getSecond();

        User user = userService.findByUserEmail(email);
        if (user == null) { // if no user in the database has this email
            return null;
        }
        if(!user.getUserPassword().equals(password)) { // if the password does not match the user's email
            return null;
        }
        return userService.logIn(user);// we return a user if the user does exist and if the password matches the email
        // if not then this returns null
    }

    @RequestMapping("/getUserPassword/{email}")
    public String getUserPassword(@PathVariable(value = "email") String email) {
        System.out.println("trying to fetch current user password" + email);
        User user = userService.findByUserEmail(email);
        String currentUserPassword = user.getUserPassword();
        return currentUserPassword;
    }

    @PostMapping("/changeUserPassword")
    public Boolean changeUserPassword(@RequestParam Map<String, String> changePasswordMap) {

        System.out.println("trying to change user password");
        try {
            String email = changePasswordMap.get("email");
            String newPassword = passwordDecoder(changePasswordMap.get("token"));
            User user = userService.findByUserEmail(email);
            System.out.println(newPassword);
            user.setUserPassword(newPassword);
            userService.save(user);
            System.out.println("done saving new password");
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @RequestMapping(value = "/deleteAccount/{userIdString}")
    public Boolean deleteAccount(@PathVariable(value = "userIdString") String userIdString) {
        System.out.println("inside deleteAccount");
        if(userIdString.equals("") || userIdString.equals("0")) {
            System.out.println("\nid is null...\n");
            return false;
        }
        System.out.println("userId is " + userIdString);
        Long userID = Long.parseLong(userIdString);
        User user = userService.findByUserID(userID);
        System.out.println("About to delete user with email: " + user.getUserEmail());
        try {
            userService.delete(user);
        } catch (Exception e) {
            System.out.println("User was not deleted!");
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println(user.getUserEmail() + " has been deleted!");
        return true;
    }

    @RequestMapping(value = "/getUserPasswordToken/{email}")
    public Integer getUserPasswordToken(@PathVariable(value = "email") String email) {
        User user = userService.findByUserEmail(email);
        if (user == null) {
            return -1;
        }
        return user.getUserForgotPasswordToken();
    }

    private Pair<String, String> myDecoder(String userToken) {
        String decodedToken = new String(base64Decoder.decode(userToken));
        System.out.println("Decoded string is: " + decodedToken);
        int i;
        for (i = 0; i < decodedToken.length(); i++) {
            if (decodedToken.charAt(i) == '%') {
                break;
            }
        }
        // We now have the index of the end of the email and the start of the password
        String email = decodedToken.substring(0,i);
        String password = decodedToken.substring(i+1);
        System.out.println("here is the decoded email: " + email);
        System.out.println("here is the decoded password: " + password);
        return Pair.of(email, password);
    }

    private String passwordDecoder(String token) {
        String decodedPassword = new String(base64Decoder.decode(token));
        System.out.println("Decoded password is: " + decodedPassword);
        return decodedPassword;
    }
}
