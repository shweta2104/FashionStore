package util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class BirthdayEmailUtil {

    public static void sendBirthdayMail(String toEmail, String name) {

        final String fromEmail = "yourgmail@gmail.com";
        final String password = "your-app-password";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

        try {
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail));

            msg.setSubject("🎉 Happy Birthday " + name + "!");

            msg.setText(
                "Dear " + name + ",\n\n" +
                "Wishing you a very Happy Birthday 🎂🎁\n\n" +
                "Thank you for shopping with FashionStore!\n\n" +
                "- Team FashionStore"
            );

            Transport.send(msg);

            System.out.println("Birthday Email Sent to: " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
