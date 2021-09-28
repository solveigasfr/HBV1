package is.hi.hbv1.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String HomeController() {
        // Business Logic
        // Call a method in a Service Class
        // Add some data to the Model
        return "home";
    }
}
