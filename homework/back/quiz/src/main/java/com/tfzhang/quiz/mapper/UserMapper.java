// UserMapper.java
package com.tfzhang.quiz.mapper;

import com.tfzhang.quiz.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper // 必须添加，配合 @MapperScan 生成代理类
public interface UserMapper {
    @Select("select * from user")
    List<User> list();

    @Insert("insert into user(userName, userPassword,isDelete,userRole,createTime, updateTime)"+
            "values(#{userName}, #{userPassword}, #{isDelete}, #{userRole}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int saveUser(User user);

    @Select("SELECT COUNT(*) FROM user WHERE userName = #{username} AND isDelete = 0")
    public int existsByName(@Param("username") String username);

    //根据id删除用户；
    @Update("UPDATE user SET isDelete = 1, updateTime = NOW() WHERE id = #{id} AND isDelete = 0")
    public int deleteUserById(@Param("id") Long id);

    //根据username删除用户；
    @Update("UPDATE user SET isDelete = 1, updateTime = NOW() WHERE userName = #{username} AND isDelete = 0")
    public int deleteByUsername(@Param("username") String username);

    @Select("SELECT COUNT(*) FROM user WHERE isDelete=0")
    public int count();

    @Select("SELECT * FROM user WHERE isDelete=0 limit #{start},#{pageSize}")
    public List<User> page(Integer start, Integer pageSize);

    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%', #{keyword}, '%') AND isDelete=0")
    List<User> findByName(String keyword);
}
