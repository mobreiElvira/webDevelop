package com.ljy.quiz_backend.service.impl;

import com.ljy.quiz_backend.mapper.QuestionMapper;
import com.ljy.quiz_backend.mapper.UserMapper;
import com.ljy.quiz_backend.model.question.QSBean;
import com.ljy.quiz_backend.model.question.Question;
import com.ljy.quiz_backend.model.user.PageBean;
import com.ljy.quiz_backend.model.Result;
import com.ljy.quiz_backend.model.user.User;
import com.ljy.quiz_backend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public Result addUser(String username, String password, String checkpassword){
        //此处的逻辑代码；
        if (StringUtils.isAnyBlank(username, password, checkpassword)) {
            return Result.error("用户名或密码为空");
        }

        if (!password.equals(checkpassword)) {
            return Result.error("两次输入的密码不一致");
        }

        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            return Result.error("用户名包含特殊字符");
        }

        //查询数据库，确定是否已经存在用户名;
        //to add...
        int userExists = userMapper.existsByName(username);
        if (userExists > 0) {
            return Result.error("用户名已存在");
        }

        //对密码进行加密;
        final String SALT = "com.quiz";
        String encrptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        User user = new User();
        user.setUserName(username);
        user.setUserPassword(encrptedPassword);
        /**
         * 注册默认是普通用户，所以userRole设置为0；
         */
        user.setUserRole(0);
        user.setIsDelete(0);

        Date now = new Date();
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        //4.插入到数据库；
        int result = userMapper.saveUser(user);

        if (result > 0)
            return Result.success("新增用户成功");
        else
            return Result.error("注册用户失败");
    }

    public boolean deleteUserById(Long id) {
        int result = userMapper.deleteUserById(id);
        return result > 0;
    }

    public boolean deleteByUsername(String username) {
        int result = userMapper.deleteByUsername(username);
        return result > 0;
    }

    //实现：
    public PageBean page(Integer page, Integer pageSize){
        //获取总的记录数；
        Integer total=userMapper.count();

        //获取分页查询结果列表；
        Integer start = (page-1)*pageSize;
        List<User> userList=userMapper.page(start, pageSize);

        //封装PageBean对象；
        PageBean pageBean = new PageBean();
        pageBean.setTotal(total);
        pageBean.setRows(userList);

        return pageBean;
    }

    public List<User> findByName(String keyword){
        List<User> userList=userMapper.findByName(keyword);
        for(User user:userList){
            user.setUserPassword("*******");
        }
        return userList;
    }

    @Override
    public User login(String username, String password) {
        //对密码进行加密;
        final String SALT = "com.quiz";
        String encrptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        return userMapper.getByNameAndPassword(username,encrptedPassword);
    }

    public int insertQuestion(QSBean qsBean){

        String ans = qsBean.getAnswer();
        if (!List.of("a", "b", "c", "d").contains(ans.toLowerCase())) {
            throw new IllegalArgumentException("Answer must be one of: a, b, c, or d");
        }

        Question q = new Question();
        q.setQuestionText(qsBean.getQuestion());

        q.setAnswer1Text(qsBean.getOptiona());
        q.setAnswer1Correct("a".equalsIgnoreCase(ans));

        q.setAnswer2Text(qsBean.getOptionb());
        q.setAnswer2Correct("b".equalsIgnoreCase(ans));

        q.setAnswer3Text(qsBean.getOptionc());
        q.setAnswer3Correct("c".equalsIgnoreCase(ans));

        q.setAnswer4Text(qsBean.getOptiond());
        q.setAnswer4Correct("d".equalsIgnoreCase(ans));

        q.setIsDelete(0);
        q.setCreateTime(LocalDateTime.now());
        q.setUpdateTime(LocalDateTime.now());

        int result = questionMapper.insertQuestion(q);
        return result;
    }
}
