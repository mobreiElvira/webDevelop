package com.ljy.quiz_backend.controller;

import com.ljy.quiz_backend.model.user.PageBean;
import com.ljy.quiz_backend.model.Result;
import com.ljy.quiz_backend.model.user.User;
import com.ljy.quiz_backend.service.UserService;
import com.ljy.quiz_backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class userController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
//    public Result addUser(String username, String password, String checkpassword) {
    public Result addUser(@RequestBody Map<String, String> registerData) {
        String username = registerData.get("username");
        String password = registerData.get("password");
        String checkpassword = registerData.get("checkpassword");
        if (StringUtils.isAnyBlank(username, password, checkpassword)){
            return Result.error("用户名或密码为空");
        }
        Result result=userService.addUser(username,password,checkpassword);
        return result;
    }
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginData){
        String username = loginData.get("username");
        String password = loginData.get("password");
        // 新增日志：打印完整请求体和解析后的参数
        System.out.println("登录请求体：" + loginData);
        System.out.println("接收的 username：" + username);
        System.out.println("接收的 password：" + password);
        if (StringUtils.isAnyBlank(username, password)) {
            return Result.error("用户名或密码为空");
        }
        User userResult = userService.login(username, password);
        if(userResult!=null){
            Claims claims = Jwts.claims();
            claims.put("id", userResult.getId());
            claims.put("username", userResult.getUserName());

            String token = JwtUtil.generateTokenWithClaims(claims);
            Result result = Result.success("用户登录成功");
            result.setData(token);
            return result;
        }else{
            return Result.error("用户登录失败");
        }
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
        boolean success = userService.deleteByUsername( username);
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


//    @Autowired
//    private UserMapper userMapper;
//
//    @RequestMapping("/register")
//    public Result addUser(String username, String password, String checkpassword) {
//        //此处的逻辑代码；
//        if (StringUtils.isAnyBlank(username, password, checkpassword)) {
//            return Result.error("用户名或密码为空");
//        }
//
//        if (!password.equals(checkpassword)) {
//            return Result.error("两次输入的密码不一致");
//        }
//
//        String regex = "^[a-zA-Z0-9]+$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(username);
//        if (!matcher.matches()) {
//            return Result.error("用户名包含特殊字符");
//        }
//
//        //查询数据库，确定是否已经存在用户名;
//        //to add...
//
//        //对密码进行加密;
//        final String SALT = "com.quiz";
//        String encrptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
//
//        User user = new User();
//        user.setUserName(username);
//        user.setUserPassword(encrptedPassword);
//        /**
//         * 注册默认是普通用户，所以userRole设置为0；
//         */
//        user.setUserRole(0);
//        user.setIsDelete(0);
//
//        Date now = new Date();
//        user.setCreateTime(now);
//        user.setUpdateTime(now);
//
//        //4.插入到数据库；
//        int result = userMapper.saveUser(user);
//
//        if (result > 0)
//            return Result.success("新增用户成功");
//        else
//            return Result.error("注册用户失败");
//
//    }

}
