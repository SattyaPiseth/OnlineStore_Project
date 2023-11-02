package co.devkh.onlinestore.reviewonlinestore.api.mail;

import jakarta.mail.MessagingException;

public interface MailService {
    void sendMail(Mail<?> mail) throws MessagingException;
}
