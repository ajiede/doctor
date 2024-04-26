package com.wy.doctor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册消息
 */
@Data
public class RegisterMessage implements Serializable {

    private static final long serialVersionUID = -4953615574208683170L;
    /**
     * 注册到服务端的用户Id
     */
    private String userId;
}