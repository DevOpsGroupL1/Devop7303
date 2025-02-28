package com.boltonuni.devop7303.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private JavaMailSender mailSender;

    public void sendAccountVerificationMail(String name, String userId, String emailAddress, String subject) {
        try {
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm:ss a");
            String text = date.format(formatter);
            LocalDateTime parsedDate = LocalDateTime.parse(text, formatter);
            String toAddress = emailAddress;
            String fromAddress = "noreply@bountyapp.ng";
            String senderName = "Group L1";
            String domain = "http://localhost:8080/devop7303/v1/api/activate-register/"+userId;
            String content = "<h3>Dear "+name+",</h3>"
                    + "<p>You registration was successful. Click the link below to verify your account.</p>"
                    + "<p><a href='"+domain+"'>Click Here</a>"+"</p><br>"
                    + "<h3>Group L1 Team</h3>"
                    + "<br><br><div style=\"text-align:center\">Copyright © 2025. All Rights Reserved. Devop7307 Group L1</div>";
//		    System.out.println(">>>>>>>>...1");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        }catch (Throwable th) {
            LOGGER.debug("EmailService error", th);
        }
    }

}
