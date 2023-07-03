package com.narola.finalproject.email;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

// EmailConnection, EmailProperties
public class EmailConfiguration {

    private static EmailConfiguration emailConfig = null;
    private Properties prop = null;

    private String username = "vjl@narola.email";
    private String password = "lZ4#yO7(bD0sJ9";

    private EmailConfiguration() {

    }

    public static EmailConfiguration getInstance() {
        if (emailConfig == null) {
            emailConfig = new EmailConfiguration();
        }
        return emailConfig;
    }

    public Properties getProperties() {
        if (prop == null) {
            prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.ionos.com");
            prop.put("mail.smtp.port", "587");

        }
        return prop;
    }

    public Session buildSession(){
        Session session = Session.getInstance(getProperties(), new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailConfiguration.getInstance().getUsername(), EmailConfiguration.getInstance().getPassword());
            }
        });
        return session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
