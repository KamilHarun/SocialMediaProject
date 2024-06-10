package com.example.messagems.Service.Impl;

import com.example.commonsms.Exceptions.EmailSendException;
import com.example.messagems.Dto.EmailDto;
import com.example.messagems.Model.Email;
import com.example.messagems.Service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.example.commonsms.Exceptions.ErrorMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendEmail(EmailDto emailDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailDTO.getTo());
        simpleMailMessage.setSubject(emailDTO.getSubject());
        simpleMailMessage.setText(emailDTO.getMessage());

        try {
            javaMailSender.send(simpleMailMessage);
            log.info(EMAIL_SEND_SUCCESS.format(emailDTO.getTo()));
        } catch (MailException e) {
            log.error(ERROR_WHILE_SENDING_EMAIL.format(emailDTO.getTo()), e);
            throw new EmailSendException(EMAIL_SEND_FAILURE.format(emailDTO.getTo()));
        }
    }

}
