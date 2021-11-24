package is.hi.hbv1.Persistence.Entities;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Email {
    public static void sendEmail(String recipient, String title, String subject, String location, String image,
                                 String userName) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "hbv1h27@gmail.com";
        String password = "Hbv1bisv";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        MimeMessage message;
        if (image != "") {
            message = prepareMessageWithImage(session, myAccountEmail, recipient, title, subject, location, image, userName);
        }
        else {
            message = prepareMessageWithoutImage(session, myAccountEmail, recipient, title, subject, location, userName);
        }
        Transport.send(message);
    }

    // message with image
    private static MimeMessage prepareMessageWithImage(Session session, String myAccountEmail, String recipient,
                                          String title, String subject, String location, String image, String userName){
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(title);

            MimeMultipart multipart = new MimeMultipart("related");

            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<H1>Report - RVK Report System</H1>"
                    + "<p>User " + userName + "just submitted this report:</p>"
                    + "<p>" + subject + "</p>"
                    + "<p> Location coordinates: " + location + "</p>"
                    + "<img src=\"cid:image\">";
            messageBodyPart.setContent(htmlText, "text/html;charset=utf-8");

            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource
                    (image);
                //("C:\\app_projects\\HBV1\\" + image); //has to be hardcoded now but will change when implemented online
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID","<image>");

            // add it
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);

            //
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // message without an image
    private static MimeMessage prepareMessageWithoutImage(Session session, String myAccountEmail, String recipient,
                                                       String title, String subject, String location, String userName){
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(title);

            MimeMultipart multipart = new MimeMultipart("related");

            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<H1>Report - RVK Report System</H1>"
                    + "<p>User " + userName + "just submitted this report:</p>"
                    + "<p>" + subject + "</p>"
                    + "<p> Location coordinates: " + location + "</p>";
            messageBodyPart.setContent(htmlText, "text/html;charset=utf-8");

            // add it
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }
}
