package com.pan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页中内容页面
 */
@Controller
@RequestMapping("/main")
public class MainController {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    /**
     * 首页内容
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String main(Model model){
        return "main";
    }

}
