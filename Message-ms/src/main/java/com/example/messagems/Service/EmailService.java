package com.example.messagems.Service;

import com.example.messagems.Dto.EmailDto;
import com.example.messagems.Model.Email;

public interface EmailService {


    void sendEmail(EmailDto emailDto);
}