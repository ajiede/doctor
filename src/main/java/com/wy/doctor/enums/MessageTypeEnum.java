package com.wy.doctor.enums;

/**
 * 消息枚举类
 */
public enum MessageTypeEnum {
    TEXT("普通文本消息"),
    HEARTBEAT("心跳数据"),
    REGISTER("注册数据");

    MessageTypeEnum(String desc) {
    }
}
