package com.tfzhang.quiz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.ssl.SslAutoConfiguration;

@SpringBootApplication(exclude = SslAutoConfiguration.class)
@MapperScan("com.tfzhang.quiz.mapper")
public class QuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }

}
