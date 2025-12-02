package com.ljy.quiz_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //使得Filter发挥作用；
@SpringBootApplication
public class QuizBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizBackendApplication.class, args);
    }

}
