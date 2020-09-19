package com.pan.controller;

import com.pan.entity.SysMenuEntity;
import com.pan.query.ResultDataQuery;
import com.pan.query.SysMenuQuery;
import com.pan.service.BaseService;
import com.pan.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单管理
 * @author tangpan
 */
@Controller
@RequestMapping("/menu")
public class MenuComtroller extends BaseController{

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(MenuComtroller.class);

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 菜单页面跳转
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("sys:menu")
    public String menu(Model model){
        return "menu/menu";
    }

    /**
     *  查询菜单列表数据
     * @return
     */
    @RequestMapping(value = "menuList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDataQuery menuList(HttpServletRequest request, HttpServletResponse response, SysMenuQuery query){
        //集合
        List<SysMenuEntity> menuList = sysMenuService.findByParames(new HashMap<>());
        //数量
        int total = sysMenuService.countByParames(new HashMap<>());
        return new ResultDataQuery(total, menuList);
    }




}
