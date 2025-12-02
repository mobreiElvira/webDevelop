package com.ljy.quiz_backend;

import com.ljy.quiz_backend.mapper.UserMapper;
import com.ljy.quiz_backend.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class QuizBackendApplicationTests {

    @Autowired  //依赖注入；
    private UserMapper userMapper;

    @Test
    public void testSaveUser(){
        User user = new User();
        user.setUserName("toms");
        user.setUserPassword("password123");

        user.setUserRole(0);
        user.setIsDelete(0);

        Date now = new Date();
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // 执行插入操作,插入后返回插入成功的行数，这里默认就应该是1；
        int result = -1;
        result = userMapper.saveUser(user);
        System.out.println(result);
        //生成的id会默认回填到user对象中；
        System.out.println(user.getId());

    }

    @Test
    public void testExistsByName(){
        String username = "toms";
        int result = userMapper.existsByName(username);
        System.out.println(result);
    }
}
