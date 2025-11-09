### Quiz后端-4、实现查询展示用户

###### 日期：2025.07.19   作者：tfzhang

后端主要提供用户与题目的注册、查询、删除等操作。使用到的开发工具：

- IDEA2024; 关注公众号”青椒工具”，发送”IDEA”，获取windows下的IDEA安装包
- mysql 5.7；关注公众号”青椒工具”，发送”mysql”，获取windows下的mysql5.7安装包；



##### 0、前情回顾：

- 1、Java访问数据库：MyBatis框架；
- 2、浏览器访问Java：Controller；
- 3、实现插入新用户接口；
- 4、实现删除用户；



##### 1、查询展示用户的两个接口：

- 按照分页的方式展示用户；
- 按照用户名查询；

<img src="img/image-20250723214644081.png" alt="image-20250723214644081" style="zoom:33%;" />



##### 2、按照分页查询用户；

**预备知识：mysql分页查询基础**

a、在mysql中采用select后添加limit参数来实现分页查询：

```sql
select * from user limit 0, 5;
//limit后两个参数的含义0表示起始数据，5表示返回5条数据；
```

b、在mysql中要查询总的记录数：

```java
select count(*) from user;
```

对比上述的前端查询页，在执行分页查询时，我们需要获取总的记录数和当前页的数据；

当前页的起始点计算：(当前页码-1)*每页条目数；

c、前端要提供给后端的数据：当前页码，每页展示数；

后端要提供给前端的数据：当前页码的数据list，以及总记录数；

参考资料：[Day10-06. 案例-员工管理-分页查询-分析_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1m84y1w7Tb?spm_id_from=333.788.videopod.episodes&vd_source=b0e6d0da66db457c6afda440766d8139&p=140)



**步骤1：在model新建PageBean对象用于controller返回数据**

```java
@Data
public class PageBean{
    private int total;
    private List<User> rows;
}
```

**步骤2：usermapper中添加代码**

```java
@Select("SELECT COUNT(*) FROM user WHERE isDelete=0")
public int count();

@Select("SELECT * FROM user WHERE isDelete=0 limit #{start},#{pageSize}")
public List<User> page(Integer start, Integer pageSize);
```

**步骤3：Controller中添加代码**

```java
@GetMapping("/users")
public Result getPage(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="5")Integer pageSize){
    PageBean pageBean=userService.page(page, pageSize);
    return Result.success(pageBean);
}
```

**步骤4：Service层中添加代码**

```java
//接口：
PageBean page(Integer page, Integer pageSize);

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
```

**步骤5：分页查询测试；**



**如果出现错误，可以打开MyBatis的日志，输出错误信息到控制台：**

[Day09-03. Mybatis-基础操作-删除(预编译SQL)_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1m84y1w7Tb?spm_id_from=333.788.player.switch&vd_source=b0e6d0da66db457c6afda440766d8139&p=124)



##### 3、按照用户名查询

**数据库预备知识：**

要在用户名中查询某个特定的字符串，可以使用如下 的sql模糊查询语句：

```sql
SELECT * FROM user WHERE username like '%abc%';
```

对应的MyBatis的mapper层的代码：

```java
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%', #{keyword}, '%') AND isDelete=0")
    List<User> findByName(String keyword);
}
```

Controller中的代码：

```java
@GetMapping("/findUser")
public Result getUser(String keyword){
    List<User> users=userService.findByName(keyword);
    return Result.success(users);
}
```

Service层中的代码：

```java
//接口;
public List<User> findByName(String keyword);

//实现：
public List<User> findByName(String keyword){
		List<User> userList=userMapper.findByName(keyword);
        for(User user:userList){
            user.setUserPassword("*******");
        }
        return userList;
}
```







