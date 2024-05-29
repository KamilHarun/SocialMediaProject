package com.example.messagems.API;

import com.example.messagems.Dto.EmailDto;
import com.example.messagems.Model.Email;
import com.example.messagems.Service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail (@Valid @RequestBody EmailDto emailDto){
        emailService.sendEmail(emailDto);
        return ResponseEntity.ok("Email sent successfully to :" + emailDto.getTo());
    }


}
