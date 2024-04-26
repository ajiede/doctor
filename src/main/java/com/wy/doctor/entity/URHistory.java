package com.wy.doctor.entity;

import lombok.Data;

@Data
public class URHistory {
    public Integer affairs_id;  //事务id
    public Integer uid;     //用户id
    public String userName;
    public Integer rid;     //runner_id
    public String runnerName;
    public Integer hid;     //医院id
    public String hospitalName;
    public double user_amount;      //用户支付金额
    public double runner_amount;    //runner收到金额
    public String basisUrl;     //依据
    public double u_score;      //用户评分

    public int pay_state;       // 支付状态 0未支付，1已支付，2完全支付
}
