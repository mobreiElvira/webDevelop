package com.ljy.quiz_backend.model.user;

import java.time.LocalDateTime;


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
    private LocalDateTime createTime;
    /*更新时间 */
    private LocalDateTime updateTime;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", isDelete=" + isDelete +
                ", userRole=" + userRole +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
