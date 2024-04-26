package com.wy.doctor.filter;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Resource
    private CustomInterceptor customInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 允许所有来源
                .allowedMethods("*") // 允许所有方法（GET、POST、PUT等）
                .allowedHeaders("*"); // 允许所有请求头
    }

    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能
     * 如果在高版本 SpringBoot (≥2.6.x) 下注册拦截器失效，则需要额外添加 @EnableWebMvc 注解才可以使用
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册路由拦截器，自定义认证规则
//        registry.addInterceptor(new SaInterceptor(handler -> {
//                    // 登录认证 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
//                    SaRouter.match("/**", "/admin/login", r -> StpUtil.checkLogin());
//                    // 甚至你可以随意的写一个打印语句
//                    SaRouter.match("/**", r -> System.out.println("--------权限认证成功-------"));
//                }).isAnnotation(true))
//                //拦截所有接口
//                .addPathPatterns("/**")
//                //不拦截/user/doLogin登录接口
//                .excludePathPatterns("/admin/login");
////         注册CustomInterceptor，自定义其他的拦截逻辑
//        registry.addInterceptor(new CustomInterceptor())
//                .addPathPatterns("/**"); // 可以设置拦截的路径
//    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册路由拦截器，自定义认证规则
//        registry.addInterceptor(customInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/admin/login");
//    }

}
