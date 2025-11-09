### Quiz后端-2、测试Spring contrller接收网络请求

###### 日期：2025.07.19   作者：tfzhang

后端主要提供用户与题目的注册、查询、删除等操作。使用到的开发工具：

- IDEA2024; 关注公众号”青椒工具”，发送”IDEA”，获取windows下的IDEA安装包
- mysql 5.7；关注公众号”青椒工具”，发送”mysql”，获取windows下的mysql5.7安装包；



##### **需求1：无参数请求**

```java
1、接收浏览器发起的/hello请求，给浏览器返回字符串"hello world"
```

**步骤1：创建controller类**

在com.tfzhang.quiz目录下创建controller这个目录，然后再创建HelloController这个类：

```java
package com.tfzhang.quiz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //表示当前类为请求处理类；
public class HelloController {

    @RequestMapping("/hello") //表示如果接收到浏览器的/hello请求，就执行下面的hello()方法；
    public String hello() {
        System.out.println("hello");
        return "Hello World";
    }
}
```

**步骤2：启动函数，实现浏览器访问**







我们的用户数据库表如下：

```mysql
DROP TABLE IF EXISTS user;

create table user
(
    id           bigint auto_increment primary key,
    userName     varchar(256)                       null comment '用户名',
    userPassword varchar(512)                       null comment '密码',
    updateTime   datetime default CURRENT_TIMESTAMP null,
    createTime   datetime default CURRENT_TIMESTAMP null,
    isDelete     tinyint                            null,
    userRole     int      default 0                 null comment '表示用户角色， 0 普通用户， 1 管理员'
)
    comment '用户表';
```

userRole表示用户的身份，1表示管理员，表示普通用户；

isDelete用于逻辑删除，也就是现实生产环境中，删除往往只是逻辑删除，不会真正从数据库中将数据删除。

##### 1、MyBatis的通流程与之前类似：

**步骤1：创建数据库**

**步骤2：创建数据库对应的实体类**

对应上述的user数据题，如下是对应的user实体类：

```java
@Data
public class User {
    /** 用户ID */
    private Long id;
    
    /** 用户名 */
    private String userName;
    
    /* 密码*/
    private String userPassword;
    
    /* 是否删除 */
    private Integer isDelete;
    
    /* 用户角色：0-普通用户，1-管理员 */
    private Integer userRole;
    
    /* 创建时间 */
    private Date createTime;
    
    /*更新时间 */
    private Date updateTime;
}
```

**步骤3：创建MyBatis配置文件**

**步骤4：编写SQL语句**

之前的MyBatis测试案例的代码：

```java
package com.tfzhang.quiz.mapper;

import com.tfzhang.quiz.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper //运行时，框架会自动实现实现类对象，并将对象实例交由IoC容器管理；
public interface UserMapper{

    //查询全部用户；
    @Select("select * from user")
    public List<User> list();
}
```

现在要新增用户，对应的sql语句如下：

```java
insert into user(userName, userPassword,isDelete,userRole,createTime,updateTime)
    values('tom', '123456', 0, 0, now(), now(),);
```

对应的java代码：

```java
@Insert("insert into user(username, userPassword,updateTime, createTime, isDelete,userRole)"+
   "values(#{userName}, #{userPassword}, #{isDelete}, #{userRole}, #{createTime}, #{updateTime})")
@Options(useGeneratedKeys = true, keyProperty = "id")
public int insert(User user);
```

**步骤5：编写测试类**

将我们的测试list()的代码写在test.com.tfzhang.quiz.QuizApplicationTests.java中；

```java
package com.tfzhang.quiz;

import com.tfzhang.quiz.mapper.UserMapper;
import com.tfzhang.quiz.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QuizApplicationTests {

    @Autowired  //依赖注入；
    private UserMapper userMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.set...
            
        userMapper.insert(user);
    }
}
```

