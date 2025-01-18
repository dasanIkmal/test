package com.nstyle.test.services;

import com.nstyle.test.services.service.IEmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements IEmailService {


    private final JavaMailSender javaMailSender;

    public EmailServiceImp(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dasanikmalmit@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
    }

    public String generateEmailBody(String name, int otp) {
        return String.format(
                "Dear %s,\n\n" +
                        "Thank you for registering on our platform! To complete your registration, please use the following One-Time Password (OTP):\n\n" +
                        "OTP: %s\n\n" +
                        "Please note that this OTP is valid for 10 minutes. If you did not request this, please ignore this email.\n\n" +
                        "Best regards,\n" +
                        "Your Company Team",
                name,
                otp
        );
    }
}
