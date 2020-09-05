package com.yuu.recruit.config;

import com.yuu.recruit.interceptor.AdminLoginInterceptor;
import com.yuu.recruit.interceptor.EmployeeLoginInterceptor;
import com.yuu.recruit.interceptor.EmployerLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器配置
 *
 * @author by yuu
 * @Classname WebApplicationConfig
 * @Date 2019/10/14 12:17
 * @see com.yuu.recruit.config
 */
@Configuration
public class WebAppConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 管理员登录拦截
        InterceptorRegistration adminRegistration = registry.addInterceptor(new AdminLoginInterceptor());
        // 拦截除了登录页面的所有页面
        adminRegistration.addPathPatterns("/admin/**");
        // 排除登录页面
        adminRegistration.excludePathPatterns("/admin/login");

        // 前台系统雇员登录拦截
        InterceptorRegistration loginRegistration = registry.addInterceptor(new EmployeeLoginInterceptor());
        // 拦截系统需要拦截的页面
        loginRegistration.addPathPatterns("/employee/**");
        // 排除雇员介绍页
        loginRegistration.excludePathPatterns("/employee/profile/**");

        // 前台系统雇主登录拦截
        InterceptorRegistration employerRegistration = registry.addInterceptor(new EmployerLoginInterceptor());
        // 拦截系统需要拦截的页面
        employerRegistration.addPathPatterns("/employer/**");
    }
}
