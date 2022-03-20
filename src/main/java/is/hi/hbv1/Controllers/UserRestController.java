package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

}
