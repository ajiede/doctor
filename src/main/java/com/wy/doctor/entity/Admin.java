package com.wy.doctor.entity;

import lombok.Data;

@Data
public class Admin {
    public Integer aid;
    public String account;   //账号
    public String password;   //密码
    public String adminName;   //名字
    public String department;  //部门
    public Integer power; //权限 1>2>3

    //String token;
}
