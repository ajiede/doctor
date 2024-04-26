package com.wy.doctor.entity;

import lombok.Data;

@Data
public class User {
    private Integer uid;   //ID     KEY1
    public String userName;    //昵称
    public String account;      //账号
    private String password;    //密码
    public Integer userAge;    //年龄
    public Integer userGender;    //性别 0男1女
    public String userPhone;   //联系电话——账号
    public String userAvatarUrl;   //头像地址
    public String userHome;    //家庭住址
    private String userIDCard;  //身份证
    public String userRemark;  //备注

    public String captcha;  // 验证码

}
