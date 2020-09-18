package com.pan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 快捷部署首页
 * @author tangpan
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    /**
     * 跳转至首页
     * @param model
     * @return
     */
    @RequestMapping()
    public String index(Model model){
        return "index";
    }


}
