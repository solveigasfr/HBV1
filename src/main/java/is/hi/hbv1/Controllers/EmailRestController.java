package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.Email;
import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
public class EmailRestController {
    private UserService userService;
    //private int debugInt = 0;
    //private int sendThisToken = -1;
    //private int token = -1;

    @Autowired
    public EmailRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sendForgotPasswordToken")
    public Boolean sendForgotPasswordToken(@RequestParam Map<String, String> parametersMap) throws MessagingException {
        System.out.println("Start of function");

        String email = parametersMap.get("email");
        // checking if the email is tied to a user in the database
        User user = userService.findByUserEmail(email);
        if (user != null) {
            //System.out.println("else statement: debugInt is: " + debugInt);
            // creating the token
            int token = createForgotPasswordToken();
            System.out.println("The token is: " + token);

            // adding the token to the user
            user.setUserForgotPasswordToken(token);
            this.userService.save(user);
            // TODO: (later) delete the token from the user after some period of time

            // sending the email with the token
            try {
                System.out.println("Sending email...");
                Email.sendForgotPasswordEmail(email, user.getUserName(), token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("End of function with success");
            return true;
        } else { // ég veit þetta else á ekki að þurfa
            System.out.println("End of function with failure");
            return false;
        }
    }

    // returns a random 6 digit number between 100000 and 999999
    private static int createForgotPasswordToken() {
        int max = 999999;
        int min = 100000;
        return (int) (Math.random()*((max - min)+1) + min);
    }

}
