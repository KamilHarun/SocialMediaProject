package com.example.postms.java.com.example.postms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PostMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostMsApplication.class, args);
	}

}
