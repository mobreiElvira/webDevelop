package com.tfzhang.quiz.controller;

import com.tfzhang.quiz.model.Result;
import com.tfzhang.quiz.model.SimpleUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public Result hello() {
        System.out.println("hello");
        return Result.success("hello");
    }
//    public String hello() {
//        System.out.println("hello");
//        return "Hello World";
//    }

    @RequestMapping("/simpleParam")
    public Result getParam() {
        System.out.println("simpleParam");
        return Result.success("simpleParam");
    }
//    public String getParam(String name, Integer age) {
//        System.out.println(name+":"+age);
//        return "ok";
//    }

    @RequestMapping("/simpleUser") //表示如果接收到浏览器的/simpleUser，就执行下面的getUser()方法；
    public Result getUser(SimpleUser user) {
        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setAge(user.getAge());
        simpleUser.setName(user.getName());
        return Result.success(simpleUser);
    }
//    public Object getUser(SimpleUser user) {
//        System.out.println(user.getName()+":"+user.getAge());
//        SimpleUser simpleUser = new SimpleUser();
//        simpleUser.setAge(user.getAge());
//        simpleUser.setName(user.getName());
//        return simpleUser;
//    }
}
