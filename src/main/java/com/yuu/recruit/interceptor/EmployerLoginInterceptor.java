package com.yuu.recruit.interceptor;

import com.yuu.recruit.domain.Employer;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 雇主登录蓝机器
 *
 * @author by yuu
 * @Classname EmployerLoginInterceptor
 * @Date 2019/10/15 21:50
 * @see com.yuu.recruit.interceptor
 */
public class EmployerLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 从 session 中获取雇员登录信息
        Employer employer = (Employer) session.getAttribute("employer");
        // 未登录
        if (employer == null) {
            // ajax 请求返回 401
            if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
                response.sendError(401);
            }

            // 普通请求
            else {
                // 跳转到登录页面
                response.sendRedirect("/login");
            }
            return false;
        }
        return true;
    }
}
