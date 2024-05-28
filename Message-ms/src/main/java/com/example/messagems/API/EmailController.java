package com.example.messagems.API;

import com.example.messagems.Dto.EmailDto;
import com.example.messagems.Model.Email;
import com.example.messagems.Service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("send-email")
    public ResponseEntity<String> sendEmail (@Valid @RequestBody EmailDto emailDto){
        emailService.sendEmail(emailDto);
        return ResponseEntity.ok("Email sent successfully to :" + emailDto.getTo());
    }

}
