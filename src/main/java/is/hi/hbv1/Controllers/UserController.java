package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.Report;
import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Endpoints to add
    // signup (GET, POST)
    // login (GET, POST)
    // loggedin (GET)

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(User user){
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(User user, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            //TODO add error messages so that the user will know what he did wrong
            return "redirect:/signup";
        }
        User exists = userService.findByUserName(user.getUserName());
        if(exists == null){
            userService.save(user);
        }
        session.setAttribute("loggedInUser", exists);
        model.addAttribute("loggedInUser", exists);
        return "signupSuccessful";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginGET(User user){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(User user, Report report, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            /*TODO add error messages to show that the user either:
               -typed wrong username
               -typed wrong password
            */
            return "login";
        }
        User exists = userService.logIn(user);
        if(exists != null){
            session.setAttribute("loggedInUser", exists);
            model.addAttribute("loggedInUser", exists);
            model.addAttribute("report", report);
            return "/newReport";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String loggedinGET(HttpSession session, Model model, Report report){
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if(sessionUser  != null){
            model.addAttribute("loggedInUser", sessionUser);
            model.addAttribute("report", report);
            return "/newReport";
        }
        return "redirect:/";
    }
}

