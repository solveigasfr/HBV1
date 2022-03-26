package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public User validatelogin(@PathVariable(value = "email") String email, @PathVariable(value = "password") String password) {

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

    @RequestMapping("/getUserPassword")
    public String getUserPassword() throws InterruptedException {
        String userPassword = getUserPassword();
        return userPassword;
    }

    @RequestMapping("/changeUserPassword")
    public String changeUserPassword(User user, String password) throws InterruptedException {
        String newUserPassword = changeUserPassword(user, password);
        return newUserPassword;
    }

    @RequestMapping(value = "/deleteAccount/{email}/{password}")
    public Boolean deleteAccount(@PathVariable(value = "email") String email, @PathVariable(value = "password") String password) {
        if(email == null || password == null) {
            return false;
        }
        User user = userService.findByUserEmail(email);
        if(!user.getUserPassword().equals(password)) {
            return false;
        }
        System.out.println("Deleting user with email: " + user.getUserEmail());
        userService.delete(user);
        System.out.println(user.getUserEmail() + " has been deleted!");
        return true;
    }
}
