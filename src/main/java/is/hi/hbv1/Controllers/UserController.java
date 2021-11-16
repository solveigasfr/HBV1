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

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signupGET(User user){
        return "signup";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signupPOST(User user, Report report, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            //TODO add error messages so that the user will know what he did wrong
            return "redirect:/signup";
        }
        User exists = userService.findByUserName(user.getUserName());
        if(exists == null){
            userService.save(user);
        }
        User exists1 = userService.findByUserName(user.getUserName());
        session.setAttribute("loggedInUser", exists1);
        model.addAttribute("loggedInUser", exists1);
        model.addAttribute("report", report);
        return "signupSuccessful";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homeGET(User user,Report report, HttpSession session, Model model){

        // Checking if a user is already logged in before accessing the login page
        User exists = (User) session.getAttribute("loggedInUser");
        if(exists != null){
            // redirect to current page if user is already logged in
            // aka, not allowing him to go back to login page
            model.addAttribute("report", report);
            return "newReport";
        }
        // Return login if no user is logged in
        return "login";
    }

    // This is copy-paste from above to allow to change the URL to login
    // This is to refresh the newReport page
    @RequestMapping(value = "/logIn", method = RequestMethod.GET)
    public String loginGET(User user,Report report, HttpSession session, Model model){

        // Checking if a user is already logged in before accessing the login page
        User exists = (User) session.getAttribute("loggedInUser");
        if(exists != null){
            // redirect to current page if user is already logged in
            // aka, not allowing him to go back to login page
            model.addAttribute("report", report);
            return "newReport";
        }
        // Return login if no user is logged in
        return "login";
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    public String loginPOST(User user, Report report, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            /*TODO add error messages to show that the user either:
               -typed wrong username
               -typed wrong password
            */
            return "redirect:/";
        }
        User exists = userService.logIn(user);
        if(exists != null){
            session.setAttribute("loggedInUser", exists);
            model.addAttribute("loggedInUser", exists);
            model.addAttribute("report", report);
            return "newReport";
        }
        return "redirect:/";
    }

    /*
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
    */

//    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
//    public String logOutPOST(HttpSession session) {
//        User sessionUser = (User) session.getAttribute("loggedInUser");
//    }
}

