package com.tfzhang.quiz.Service;

import com.tfzhang.quiz.model.Result;

public interface UserService {
    //保存新用户；
    public Result addUser(String username, String password, String checkpassword);
}
