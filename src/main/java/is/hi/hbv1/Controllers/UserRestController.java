package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

}
