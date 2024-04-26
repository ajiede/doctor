package com.wy.doctor.entity;

import lombok.Data;

@Data
public class Doctor {
    public Integer doctor_id;
    public Integer hid;
    public String doctorName;   //医生名称
    public String department;   // 部门
    public String office_hours; // 出诊时间
    // 按照出诊的周几，填写对应的内容
    public Integer monday;
    public Integer tuesday;
    public Integer wednesday;
    public Integer thursday;
    public Integer friday;
    public Integer saturday;
    public Integer sunday;
    public String remark;   //备注
}
