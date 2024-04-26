package com.wy.doctor.entity;

import lombok.Data;

import java.util.Date;

@Data
public class  Hospital {
    private Integer hid;
    public String hospitalName;
    public String account;      //账号
    private String password;
    public String hospitalIDCard;      //身份编码
    public Date hospitalCreateTime;  //建院时间
    public String hospitalAddress;     //医院地址
    public String hospitalPhone;   //联系方式
    public String hospitalEmail;   //邮箱
    public String hospitalURL;     //网址
    public Integer zipCode;    //邮编 6位
    public String hospitalRemark;      //备注
    public String hospitalOverview;    //概述
}
