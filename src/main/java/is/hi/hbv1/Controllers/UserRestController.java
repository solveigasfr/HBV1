package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.User;
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

    @RequestMapping("/getUserPassword/{userToken}")
    public String getUserPassword(@PathVariable(value = "userToken") String userToken) {
        System.out.println("Encoded string is: " + userToken);
        Pair<String, String> decodedEmailAndPassword = myDecoder(userToken);
        String userPassword = decodedEmailAndPassword.getSecond();
        return userPassword;
    }

    @RequestMapping("/changeUserPassword")
    public String changeUserPassword(User user, String password) throws InterruptedException {
         return "to be added";
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
