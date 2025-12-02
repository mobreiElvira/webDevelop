package com.tfzhang.quiz.service;

import com.tfzhang.quiz.model.PageBean;
import com.tfzhang.quiz.model.Result;
import com.tfzhang.quiz.model.User;

import java.util.List;

public interface UserService {
    //保存新用户；
    public Result addUser(String username, String password, String checkpassword);
    public boolean deleteUserById(Long id);
    public boolean deleteUserByName(String username);

    PageBean page(Integer page, Integer pageSize);

    public List<User> findByName(String keyword);
}
