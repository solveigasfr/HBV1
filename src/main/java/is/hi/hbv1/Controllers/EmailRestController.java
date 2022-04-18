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
    private final UserService userService;
    private int debugInt = 0;

    @Autowired
    public EmailRestController(UserService userService) {
        System.out.println("Running the constructor ... beep boop " + debugInt);
        this.userService = userService;
    }

    @RequestMapping("/sendForgotPasswordToken/{email}")
    public int sendForgotPasswordToken(@PathVariable(value = "email") String email) throws MessagingException {
        debugInt++;
        System.out.println("start of function: debugInt is: " + debugInt);
        //if (debugInt % 2 == 0) { // this would be a cheesy fix, which would cause problems in the future
        //    return -1;
        //}
        System.out.println("A little further, debugInt is: " + debugInt);
        User user = new User();
        // checking if the email is tied to a user in the database
        try {
            user = userService.findByUserEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int token = -1;
        if (user != null) {
            System.out.println("else statement: debugInt is: " + debugInt);
            // creating the token
            token = createForgotPasswordToken();
            System.out.println("The token is: " + token);

            // adding the token to the user
            user.setUserForgotPasswordToken(token);
            userService.save(user);
            // TODO: (later) delete the token from the user after some period of time

            // sending the email with the token
            Email.sendForgotPasswordEmail(email, user.getUserName(), token);
        }
        return token;
    }

    // returns a random 6 digit number between 100000 and 999999
    private static int createForgotPasswordToken() {
        int max = 999999;
        int min = 100000;
        return (int) (Math.random()*((max - min)+1) + min);
    }

}
