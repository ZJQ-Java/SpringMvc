package com.exampleQiu.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HelloController
 * Author:   zhangjinqiu03
 * Date:     2018/12/29 14:07
 */
public class HelloController extends AbstractController{

    @Override
    protected ModelAndView handleRequestInternal(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("hello");
    }
}
