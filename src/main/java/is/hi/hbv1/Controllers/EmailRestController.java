package is.hi.hbv1.Controllers;

import is.hi.hbv1.Persistence.Entities.Email;
import is.hi.hbv1.Persistence.Entities.User;
import is.hi.hbv1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailRestController {
    private UserService userService;

    @Autowired
    public EmailRestController(UserService userService) {this.userService = userService; }

    @RequestMapping("/sendForgotPasswordToken/{email}")
    public int sendForgotPasswordToken(@PathVariable(value = "email") String email) throws MessagingException {
        // checking if the email is tied to a user in the database
        User user = userService.findByUserEmail(email);
        User exists = userService.logIn(user);
        if (exists == null) {
            return -1;
        }
        else {
            // creating the token
            int token = createForgotPasswordToken();
            System.out.println("The token is: " + token);

            // adding the token to the user
            exists.setUserForgotPasswordToken(token);
            userService.save(exists);
            // TODO: (later) delete the token from the user after some period of time

            // sending the email with the token
            Email.sendForgotPasswordEmail(email, user.getUserName(), token);
            return token;
        }
    }

    // returns a random 6 digit number between 100000 and 999999
    private static int createForgotPasswordToken() {
        int max = 999999;
        int min = 100000;
        return (int) (Math.random()*((max - min)+1) + min);
    }

}
