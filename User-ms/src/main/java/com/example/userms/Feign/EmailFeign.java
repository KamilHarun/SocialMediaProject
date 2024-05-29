package com.example.userms.Feign;

import com.example.commonsms.Dto.EmailDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.example.commonsms.Constants.FeignConstants.EMAIL_SERVICE;

@FeignClient(name = EMAIL_SERVICE , configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface EmailFeign {

    @PostMapping("ap1/v1/emails/send")
    public ResponseEntity<String> sendEmail (@Valid @RequestBody EmailDto emailDto);
}
