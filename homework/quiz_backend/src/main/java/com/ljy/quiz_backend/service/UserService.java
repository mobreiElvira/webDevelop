package com.ljy.quiz_backend.service;

import com.ljy.quiz_backend.model.user.PageBean;
import com.ljy.quiz_backend.model.Result;
import com.ljy.quiz_backend.model.user.User;

import java.util.List;

public interface UserService {

    //保存新用户；
    Result addUser(String username, String password, String checkpassword);
    public boolean deleteUserById(Long id);
    public boolean deleteByUsername(String username);
    PageBean page(Integer page, Integer pageSize);
    public List<User> findByName(String keyword);


    User login(String username, String password);
}
