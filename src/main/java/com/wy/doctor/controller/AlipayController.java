package com.wy.doctor.controller;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.wy.doctor.entity.AlipayEntity;
import com.wy.doctor.entity.URHistory;
import com.wy.doctor.service.URHistoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AlipayController {
    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "utf-8";
    private static final String SIGN_TYPE = "RSA2";

    @Resource
    private AlipayEntity alipayUtils;
    //订单
    @Resource
    private URHistoryService urHistoryService;

    @GetMapping("/pay")
    public void pay(@RequestParam String id, HttpServletResponse httpServletResponse) throws IOException {
        // 查询订单信息
        URHistory urHistory = urHistoryService.getURHistoryById(Integer.parseInt(id));
        // 创建client，通过SDK提供的client调用支付宝api
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, alipayUtils.getAppId(), alipayUtils.getAppPrivateKey(), FORMAT, CHARSET, alipayUtils.getAlipayPublicKey(), SIGN_TYPE);
        // 创建Request并设置参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(alipayUtils.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", urHistory.getAffairs_id()); // 订单编号
        bizContent.put("total_amount", urHistory.getUser_amount()); // 订单金额
        bizContent.put("subject", urHistory.getAffairs_id()); // 支付名称
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");// 固定配置
        request.setBizContent(bizContent.toString());
//        request.setReturnUrl("http://localhost:8080/orders");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e){
            e.printStackTrace();
        }
        httpServletResponse.setContentType("text/html;charset=" + CHARSET);
        httpServletResponse.getWriter().write(form); // 将完整表达输入到页面
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }

    @PostMapping("/notify")
    public void notify(HttpServletRequest request) throws AlipayApiException {
        if(request.getParameter("trade_status").equals("TRADE_SUCCESS")){
            System.out.println("支付宝异步回调");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for(String name : requestParams.keySet()){
                params.put(name, request.getParameter(name));
            }
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayUtils.getAlipayPublicKey(), "utf-8");
            if(checkSignature){
                // 验签成功
                // 1. 获取订单编号
                String outTradeNo = params.get("out_trade_no");
                URHistory urHistory = urHistoryService.getURHistoryById(Integer.parseInt(outTradeNo));
                // 2. 修改订单状态
                urHistoryService.updateURHistoryById(urHistory.getAffairs_id(),2);
            }
        }
    }
}
