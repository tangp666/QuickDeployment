package com.pan.controller;

import com.alibaba.fastjson.JSONObject;
import com.pan.entity.ResultEntity;
import com.pan.entity.SysMenuEntity;
import com.pan.enums.ResultEnum;
import com.pan.query.SysMenuQuery;
import com.pan.service.SysMenuService;
import com.pan.util.MacroelementUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

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
     * @param request
     * @param response
     * @param query 条件
     * @return
     */
    @RequestMapping(value = "/menuList", method = RequestMethod.GET)
    @ResponseBody
    public ResultEntity menuList(HttpServletRequest request, HttpServletResponse response, SysMenuQuery query){
        //集合
        List<SysMenuEntity> menuList = sysMenuService.findByParames(new HashMap<>());
        //数量
        int total = sysMenuService.countByParames(new HashMap<>());
        JSONObject object = new JSONObject();
        object.put("total",total);
        object.put("rows", menuList);
        return new ResultEntity(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), object);
    }

    /**
     * 跳转添加页面
     * @param model
     * @param id 参数条件 parentId 父节点id
     * @return
     */
    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    public String menuAdd(Model model, @PathVariable("id") long id){
        SysMenuEntity sysMenuEntity = new SysMenuEntity();
        //当前添加的菜单级别为最高级别时
        if(id == 0){
            sysMenuEntity.setId(id);
            sysMenuEntity.setName("一级菜单");
        }else {
            sysMenuEntity = sysMenuService.findById(id);
        }
        model.addAttribute("sysMenuEntity", sysMenuEntity);
        return "menu/add";
    }

    /**
     * 菜单保存
     * @param request
     * @param response
     * @param sysMenuEntity 条件
     * @return 是否保存成功
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity menuSave(HttpServletRequest request, HttpServletResponse response, SysMenuEntity sysMenuEntity){
        int insert = sysMenuService.insert(sysMenuEntity);
        //保存成功？ 失败
        if (insert > 0){
            return new ResultEntity(ResultEnum.SAVESUCCESS.getCode(), ResultEnum.SAVESUCCESS.getMessage());
        }else{
            return new ResultEntity(ResultEnum.SAVEERROR.getCode(), ResultEnum.SAVEERROR.getMessage());
        }
    }

    /**
     * 跳转修改页面
     * @param model
     * @param id 菜单主键
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String menuEdit(Model model, @PathVariable("id") long id){
        SysMenuEntity sysMenuEntity = sysMenuService.findById(id);
        model.addAttribute("sysMenuEntity",sysMenuEntity);
        SysMenuEntity parentEntity = new SysMenuEntity();
        //当前父节点为0时
        if(String.valueOf(MacroelementUtils.ZERO).equals(String.valueOf(sysMenuEntity.getParentId()))){
            parentEntity.setParentId(sysMenuEntity.getParentId());
            parentEntity.setName("一级菜单");
        }else {
            parentEntity = sysMenuService.findById(sysMenuEntity.getParentId());
        }
        model.addAttribute("parentEntity",parentEntity);
        return "menu/edit";
    }

    /**
     * 修改菜单
     * @param request
     * @param response
     * @param sysMenuEntity 菜单参数
     * @return 是否保存成功
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity updateMenu(HttpServletRequest request, HttpServletResponse response, SysMenuEntity sysMenuEntity){
        int update = sysMenuService.update(sysMenuEntity);
        //保存成功？ 失败
        if (update > 0){
            return new ResultEntity(ResultEnum.SAVESUCCESS.getCode(), ResultEnum.SAVESUCCESS.getMessage());
        }else{
            return new ResultEntity(ResultEnum.SAVEERROR.getCode(), ResultEnum.SAVEERROR.getMessage());
        }
    }

    /**
     * 删除菜单
     * @param id 菜单id
     * @return 返回删除信息
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity deleteMenu(long id){
        int i = sysMenuService.deleteById(id);
        //保存成功？ 失败
        if (i > 0){
            return new ResultEntity(ResultEnum.DELETESUCCESS.getCode(), ResultEnum.DELETESUCCESS.getMessage());
        }else{
            return new ResultEntity(ResultEnum.DELETEERROR.getCode(), ResultEnum.DELETEERROR.getMessage());
        }
    }

    /**
     * 批量删除菜单
     * @param ids 菜单id集合
     * @return 返回删除信息
     */
    @RequestMapping(value = "batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity batchDeleteMenu(List<Long> ids){
        int i = sysMenuService.deleteBatch(ids);
        //保存成功？ 失败
        if (i > 0){
            return new ResultEntity(ResultEnum.DELETESUCCESS.getCode(), ResultEnum.DELETESUCCESS.getMessage());
        }else{
            return new ResultEntity(ResultEnum.DELETEERROR.getCode(), ResultEnum.DELETEERROR.getMessage());
        }
    }

}
