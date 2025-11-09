// UserMapper.java
package com.tfzhang.quiz.mapper;

import com.tfzhang.quiz.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper; // 正确的包
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper // 必须添加，配合 @MapperScan 生成代理类
public interface UserMapper {
    @Select("select * from user")
    List<User> list();

    @Insert("insert into user(userName, userPassword,isDelete,userRole,createTime, updateTime)"+
            "values(#{userName}, #{userPassword}, #{isDelete}, #{userRole}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int saveUser(User user);
}
