package com.wy.doctor.entity;

import lombok.Data;

@Data
public class Runner {
    private Integer rid;
    public String runnerName;
    public Integer runnerAge;  //年龄
    public Integer runnerGender;  //性别
    public String runnerPhone; //电话
    public String account;      //账号
    private String password;    //密码
    public String runnerAvatarUrl;   //头像链接
    public String occupation;  //职业
    public String runnerRemark;    //备注
    public String runnerHome;  //家庭住址
    private String runnerIDCardNumber;  //身份证号码
    private String runnerIDCardPictureUrl;  //身份证图片链接
    public double score;   //评分1.0-5.0
    private double balance; //余额

    // 验证码
    public String captcha;
}
