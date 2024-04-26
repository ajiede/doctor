package com.wy.doctor.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CustomInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 在处理请求之前执行，可以获取请求头信息
//        String authorizationHeader = request.getHeader("Authorization");
//        System.out.println("token:"+authorizationHeader);
        // 处理逻辑...
            System.out.println("aaaaaa");
            String token = request.getHeader("Authorization");
            System.out.println("bbbbbb");
            System.out.println(token);
            // 在这里处理 token，比如验证它的有效性
            // ...
            StpUtil.checkLogin();
        return true; // 返回true表示继续执行后续流程，返回false表示中断后续流程
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 在处理完请求后调用，但在视图渲染之前（Controller方法调用之后）
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 在整个请求处理完成后调用，即视图渲染完毕或者调用方已经拿到响应
    }
}
