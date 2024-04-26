package com.wy.doctor.controller;

import com.wy.doctor.entity.Runner;
import com.wy.doctor.entity.User;
import com.wy.doctor.service.RunnerService;
import com.wy.doctor.service.UserService;
import com.wy.doctor.service.impl.RedisServiceImpl;
import com.wy.doctor.utils.MyRandomUtil;
import com.wy.doctor.utils.UniSmsAdapter;
import com.wy.doctor.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @Autowired
    private RunnerService runnerService;
    @Resource
    private UniSmsAdapter uniSmsAdapter;

    @Autowired
    private RedisServiceImpl redisService;

    private MyRandomUtil myRandom = new MyRandomUtil();

    // 登录
    @GetMapping("/captcha1")
    public Result<Object> getCaptcha1(@RequestParam("phone") String phone){
        System.out.println("login:" + phone);
        String captcha = myRandom.randomCaptcha(6);
        User user = userService.getUserByPhone(phone);
        Runner runner = runnerService.getRunnerByPhone(phone);
        if( user == null && runner == null){
            return Result.failed("请先注册账号，再登录");
        }
        redisService.saveVerificationCode(phone,captcha);
//        暂定为 该手机号
        uniSmsAdapter.send(captcha,60+"","13547933839","验证码",null);
        return Result.ok("发送成功，注意查看手机短信");
    }

    // 注册
    @GetMapping("/captcha2")
    public Result<Object> getCaptcha2(@RequestParam("phone") String phone){
        System.out.println("register:" + phone);
        String captcha = myRandom.randomCaptcha(6);
        User user = userService.getUserByPhone(phone);
        Runner runner = runnerService.getRunnerByPhone(phone);
        if( user != null || runner != null){
            return Result.failed("该手机已被注册，若不是你的手机号，请重新输入手机号");
        }
        redisService.saveVerificationCode(phone,captcha);
        uniSmsAdapter.send(captcha,60+"","13547933839","验证码",null);
        return Result.ok("发送成功，注意查看手机短信");
    }
}
