package com.example.postms.Feign;

import com.example.commonsms.Dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.commonsms.Constants.FeignConstants.USER_SERVICE;

@FeignClient(name = USER_SERVICE , configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface UserFeign {
    @GetMapping("/api/v1/users/findById")
    UserResponseDto findById(@RequestHeader("Authorization") String authorizationHeader,
                             @RequestParam("id") Long id);
}

