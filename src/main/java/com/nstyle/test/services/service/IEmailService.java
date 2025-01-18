package com.nstyle.test.services.service;

public interface IEmailService {

    void sendEmail(String toEmail,
                   String subject,
                   String body);

    String generateEmailBody(String name, int otp);
}
