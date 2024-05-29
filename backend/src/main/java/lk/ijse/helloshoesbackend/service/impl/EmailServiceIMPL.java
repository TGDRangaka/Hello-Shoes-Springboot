package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.EmailDTO;
import lk.ijse.helloshoesbackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceIMPL implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleEmail(EmailDTO email) {
        log.info("Attempting to send email to {} about {}", email.getTo(), email.getSubject());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("helloshoespvtltd@gmail.com");
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            emailSender.send(message);
            log.info("Email sent successfully");
        }catch (Exception e){
            log.error("Error occurred while sending email");
            throw e;
        }

    }
}
