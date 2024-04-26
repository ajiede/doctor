package com.wy.doctor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 心跳消息
 */
@Data
public class HeartbeatMessage implements Serializable {

    private static final long serialVersionUID = 1290124171105321491L;


    /**
     * 发送心跳消息的用户Id
     */
    private String userId;

}