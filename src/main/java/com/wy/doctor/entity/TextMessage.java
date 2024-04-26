package com.wy.doctor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 文本消息实体类
 */
@Data
public class TextMessage implements Serializable {

    private static final long serialVersionUID = -4851870722684661727L;

    /**
     * 发送消息的用户Id
     */
    private String userId;

    /**
     * 消息的接收者
     */
    private String receiver;

    /**
     * 用户发送的消息
     */
    private String msg;

}
