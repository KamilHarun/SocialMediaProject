package com.example.userms.Feign;

import com.example.commonsms.Dto.EmailDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.example.commonsms.Constants.FeignConstants.EMAIL_SERVICE;

@FeignClient(name = EMAIL_SERVICE, configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface EmailFeign {

    @PostMapping("/api/v1/emails/send")
    ResponseEntity<String> sendEmail( @Valid @RequestBody EmailDto emailDto);
}
