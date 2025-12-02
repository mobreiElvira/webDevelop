package com.tfzhang.quiz.controller;

import com.tfzhang.quiz.mapper.UserMapper;
import com.tfzhang.quiz.model.PageBean;
import com.tfzhang.quiz.model.Result;
import com.tfzhang.quiz.model.User;
import com.tfzhang.quiz.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public Result addUser(String username, String password, String checkpassword) {
        Result result = userService.addUser(username, password, checkpassword);
        return result;
    }

    @GetMapping("/deleteById")
    public Result deleteUserById(Long id) {
        boolean success = userService.deleteUserById(id);
        if (success) {
            return Result.success("用户已删除");
        }
        return Result.error("用户不存在或已被删除");
    }

    @GetMapping("/deleteByName")
    public Result deleteUser(String username) {
        boolean success = userService.deleteUserByName(username);
        if (success) {
            return Result.success("用户已删除");
        }
        return Result.error("用户不存在或已被删除");
    }

    @GetMapping("/users")
    public Result getPage(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="5")Integer pageSize){
        PageBean pageBean=userService.page(page, pageSize);
        return Result.success(pageBean);
    }

    @GetMapping("/findUser")
    public Result getUser(String keyword){
        List<User> users=userService.findByName(keyword);
        return Result.success(users);
    }

}
