package com.wy.doctor.utils;

import java.util.Random;

public class MyRandomUtil {
    private String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private String passwordStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@%&*.";
    private String characters_num = "0123456789";

    // 使用 StringBuilder 存储生成的字符串
    StringBuilder randomString = new StringBuilder();

    public String randomString(int length){

        // 创建 Random 对象
        Random random = new Random();

        // 从字符集中随机选取字符，拼接成字符串
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

    public String randomPassword(int length){
        // 创建 Random 对象
        Random random = new Random();

        // 从字符集中随机选取字符，拼接成字符串
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(passwordStr.length());
            randomString.append(passwordStr.charAt(index));
        }

        return randomString.toString();
    }

    public String randomCaptcha(int length){
        // 创建 Random 对象
        Random random = new Random();

        // 从字符集中随机选取字符，拼接成字符串
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters_num.length());
            randomString.append(characters_num.charAt(index));
        }

        return randomString.toString();
    }

}
