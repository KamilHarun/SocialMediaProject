package com.example.commentms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CommentMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentMsApplication.class, args);
    }

}
