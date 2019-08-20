package com.java.system.params;


import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * Created by Mr.BH
 * 管理员 请求参数实体
 */
public class AdminRequest {

    @NotNull(message = "账户不能为空")
    private String ukAccount;

//    @NotNull
    private String password;

    @NotNull
    private String nickname;

    @NotNull
    private String phone;

    @NotNull
    @Email
    private String email;


    private String avatar;

    private String address;

//    @NotNull
    private Integer status;

    @NotNull
    private Integer roleId;

//    @NotNull
    private String roleName;

    public String getUkAccount() {
        return ukAccount;
    }

    public void setUkAccount(String ukAccount) {
        this.ukAccount = ukAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "AdminRequest{" +
                "ukAccount='" + ukAccount + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}

