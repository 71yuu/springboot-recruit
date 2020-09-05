package com.yuu.recruit.interceptor;

import com.yuu.recruit.domain.Admin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理员登录拦截器，需要登录了才登录访问首页、列表页....
 *
 * @author by yuu
 * @Classname AdminLoginInceptor
 * @Date 2019/10/14 12:15
 * @see com.yuu.recruit.interceptor
 */
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 从 session 中获取管理员登录信息
        Admin admin = (Admin) session.getAttribute("admin");
        // 未登录
        if (admin == null) {
            // 跳转到登录页面
            response.sendRedirect("/admin/login");
            return false;
        }
        return true;
    }
}
