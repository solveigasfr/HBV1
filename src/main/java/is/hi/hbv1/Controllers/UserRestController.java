package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {

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

    // TODO: implement getUserPassword() in UserRestController
    @RequestMapping("/getUserPassword")
    public String getUserPassword() throws InterruptedException {
        //String userPassword = getUserPassword();
        //return userPassword;
        return "test";
    }

    @RequestMapping("/changeUserPassword")
    public String changeUserPassword(User user, String password) throws InterruptedException {
        String newUserPassword = changeUserPassword(user, password);
        return newUserPassword;
    }

    @RequestMapping(value = "/deleteAccount/{userIdString}")
    public Boolean deleteAccount(@PathVariable(value = "userIdString") String userIdString) {
        System.out.println("inside deleteAccount");
        if(userIdString.equals("")) {
            System.out.println("\nid is null...\n");
            return false;
        }
        System.out.println("userId is " + userIdString);
        System.out.println("changing the id from string to Long");
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
}
