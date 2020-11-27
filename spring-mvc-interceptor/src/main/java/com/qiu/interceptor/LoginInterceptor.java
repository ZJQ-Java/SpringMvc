package com.qiu.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    //在请求处理的方法之前执行
    //如果返回true执行下一个拦截器
    //如果返回false就不执行下一个拦截器
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        System.out.println("------------处理前------------");
        if (httpServletRequest.getRequestURI().contains("login")) {
            return true;
        }
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("user") != null) {
            return true;
        }
        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(httpServletRequest, httpServletResponse);
        return false;
    }

    //在请求处理方法执行之后执行
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
            ModelAndView modelAndView) throws Exception {
        System.out.println("------------处理后------------");
    }

    //在dispatcherServlet处理后执行,做清理工作.
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {
        System.out.println("------------清理------------");
    }
}
