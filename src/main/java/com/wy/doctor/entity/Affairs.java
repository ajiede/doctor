package com.wy.doctor.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Affairs {
    public int affairs_id;
    public int uid;
    public int hid;
    // 就医的类型
    public String type_medical;
    public int doctor_id;
    // 当前的价格，需要用户预先支付
    public double user_amount;
    // user的所在地址
    public String userAddress;
    public String hospitalAddress;
    // 事务的开始时间
    public Date createTime;
    // 由runner到地点后，点击开始
    public Date startTime;
    // 由runner提交依据后，user点击到家后生成时间
    public Date endTime;
    // 1.待定，待领取 2.正在进行  3.runner已完成 4.user已完成 0.紧急
    public int state;
    // 备注
    public String remark;

    public String userName;
    public String userAvatarUrl;
    public String hospitalName;
    public String doctorName;
}
