package com.narola.finalproject.email;

import com.narola.finalproject.exception.MailException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    public void sendEmail(String toAddress, String subject, String body) throws MailException {
        try {
            Session session = EmailConfiguration.getInstance().buildSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConfiguration.getInstance().getUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new MailException("Exception while sending mail:", e);
        }
    }
}

