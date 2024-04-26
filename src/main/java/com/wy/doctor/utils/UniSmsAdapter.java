package com.wy.doctor.utils;

import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UniSmsAdapter {
    @Value("${sms.signature}")
    private String SIGNATURE;

    @Value("${sms.template}")
    private String TEMPLATE;

    /**
     * 发送短信
     * @param code 如 验证码
     * @param ttl 过期时间
     * @param phone 手机号
     * @param signature 短信签名
     * @param template 短信模板
     */
    public void send(String code, String ttl, String phone, String signature, String template) {
        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<>();
        templateData.put("code", code);
        templateData.put("ttl", ttl);

        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo(phone)
                .setSignature(("".equals(signature) || signature == null) ? SIGNATURE : signature)
                .setTemplateId(("".equals(template) || template == null) ? TEMPLATE : template)
                .setTemplateData(templateData);

        // 发送短信
        try {
            UniResponse res = message.send();
            System.out.println(res);
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }
    }
}
