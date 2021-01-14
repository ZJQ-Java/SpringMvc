package com.qiu.interceptor;

import com.qiu.MyException.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ExceptionInterceptor implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object o, Exception e) {
        if (e instanceof CustomException) {
            CustomException e1 = (CustomException) e;
            System.out.println(e1.getMsg());
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("error");
        return model;
    }
}
