package com.pan.controller;

import com.pan.query.SysMenuQuery;
import com.pan.service.SysMenuService;
import com.pan.util.MacroelementUtils;
import com.pan.util.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 跳转至首页
     * @param model
     * @return
     */
    @RequestMapping()
    public String index(Model model){
        List<SysMenuQuery> menuTreeList = sysMenuService.findMenuTreeByUser(ShiroUtils.getUserId(), MacroelementUtils.ZEROandONE);
        model.addAttribute("menuTreeList", menuTreeList);
        return "index";
    }


}
