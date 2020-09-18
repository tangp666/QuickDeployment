package com.pan.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础controller
 * @author tangpan
 */
public class BaseController {

    /** 基于@ExceptionHandler异常处理 */
    @ExceptionHandler
    public String exp(HttpServletRequest request, Exception ex) {
        request.setAttribute("ex", ex);
        return "login";
    }

}
