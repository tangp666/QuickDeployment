package com.pan.controller;

import com.alibaba.fastjson.JSONObject;
import com.pan.entity.ResultEntity;
import com.pan.entity.SysRoleEntity;
import com.pan.entity.SysRoleMenuEntity;
import com.pan.enums.ResultEnum;
import com.pan.query.SysRoleQuery;
import com.pan.service.SysRoleMenuService;
import com.pan.service.SysRoleService;
import com.pan.util.ShiroUtils;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 角色管理
 * @author tangpan
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    /**
     * 跳转角色页面
     * @param model
     * @return
     */
    @RequestMapping("/role")
    @PerformanceSensitive("role:role")
    public String role(Model model){
        return "role/role";
    }

    /**
     * 角色列表
     * @param request
     * @param response
     * @param query 条件
     * @return
     */
    @RequestMapping("/roleList")
    @ResponseBody
    public JSONObject roleList(HttpServletRequest request, HttpServletResponse response, SysRoleQuery query){
        //拼接参数
        Map<String,Object> map = new HashMap<>();
        map.put("roleName",query.getRoleName());

        map.put("limit",query.getLimit());
        map.put("offset",query.getOffset());
        map.put("sort", query.getSort());
        map.put("order", query.getOrder());
        //数据集合
        List<SysRoleEntity> sysRoleEntities = sysRoleService.findByParames(map);
        //total
        int total = sysRoleService.countByParames(map);
        //封装数据
        JSONObject object = new JSONObject();
        object.put("data", sysRoleEntities);
        object.put("total", total);
        return object;
    }

    /**
     * 跳转添加页面
     * @param model
     * @return
     */
    @RequestMapping("/addRole")
    public String addRole(Model model){
        return "role/add";
    }

    /**
     * 角色保存
     * @param entity 角色菜单信息
     * @param request
     * @return 返回对象
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResultEntity saveRole(SysRoleEntity entity, HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        //角色保存
        entity.setCreateUserId(ShiroUtils.getUserId());
        entity.setCreateTime(new Date());
        int insert = 0;
        try {
            insert = sysRoleService.insert(entity);
        } catch (Exception e) {
            LOGGER.error("角色保存失败");
            e.printStackTrace();
        }
        if(insert > 0){
            //选中的菜单
            String menuIds = request.getParameter("menuIds");
            List<String> ids = Arrays.asList(menuIds.split(","));
            List<SysRoleMenuEntity> sysRoleMenuEntities = new ArrayList<>();
            for (String menuId : ids) {
                SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
                sysRoleMenuEntity.setMenuId(Long.parseLong(menuId));
                sysRoleMenuEntity.setRoleId(entity.getId());
                sysRoleMenuEntities.add(sysRoleMenuEntity);
            }
            try {
                int i = sysRoleMenuService.batchInsert(sysRoleMenuEntities);
                resultEntity.setCode(ResultEnum.SUCCESS.getCode());
                resultEntity.setMessage(ResultEnum.SUCCESS.getMessage());
            } catch (Exception e) {
                resultEntity.setCode(ResultEnum.EXCEPTION.getCode());
                resultEntity.setMessage(ResultEnum.EXCEPTION.getMessage());
                LOGGER.error("菜单角色关联保存失败");
                e.printStackTrace();
            }
        }
        return resultEntity;
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping("edit/{id}")
    public String editRole(Model model, @PathVariable("id") long id){
        SysRoleEntity sysRoleEntity = sysRoleService.findById(id);
        model.addAttribute("sysRoleEntity",sysRoleEntity);
        return "role/edit";
    }

    /**
     * 角色保存
     * @param entity 角色菜单信息
     * @param request
     * @return 返回对象
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultEntity updateRole(SysRoleEntity entity, HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        int update = 0;
        try {
            update = sysRoleService.update(entity);
        } catch (Exception e) {
            LOGGER.error("角色修改失败");
            e.printStackTrace();
        }
        if(update > 0){
            //清空之前的选中
            Map map = new HashMap();
            map.put("roleId", entity.getId());
            sysRoleMenuService.deleteByParames(map);

            //选中的菜单
            String menuIds = request.getParameter("menuIds");
            String[] menus = menuIds.split(",");
            List<SysRoleMenuEntity> sysRoleMenuEntities = new ArrayList<>();
            for (String menuId : menus) {
                SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
                sysRoleMenuEntity.setMenuId(Long.parseLong(menuId));
                sysRoleMenuEntity.setRoleId(entity.getId());
                sysRoleMenuEntities.add(sysRoleMenuEntity);
            }
            try {
                int i = sysRoleMenuService.batchInsert(sysRoleMenuEntities);
                resultEntity.setCode(ResultEnum.SUCCESS.getCode());
                resultEntity.setMessage(ResultEnum.SUCCESS.getMessage());
            } catch (Exception e) {
                resultEntity.setCode(ResultEnum.EXCEPTION.getCode());
                resultEntity.setMessage(ResultEnum.EXCEPTION.getMessage());
                LOGGER.error("菜单角色关联保存失败");
                e.printStackTrace();
            }
        }
        return resultEntity;
    }

    /**
     * 删除角色
     * @param id 角色id
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity removeRole(@RequestParam("id") long id){
        //返回对象
        ResultEntity resultEntity = new ResultEntity();
        int i = 0;
        try {
            //删除角色
            i = sysRoleService.deleteById(id);
            if(i > 0){
                Map<String,Object> map = new HashMap<>();
                map.put("roleId",id);
                //删除角色菜单关联信息
                sysRoleMenuService.deleteByParames(map);
            }
            resultEntity.setCode(ResultEnum.SUCCESS.getCode());
            resultEntity.setMessage(ResultEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            resultEntity.setCode(ResultEnum.EXCEPTION.getCode());
            resultEntity.setMessage(ResultEnum.EXCEPTION.getMessage());
            e.printStackTrace();
        }
        return resultEntity;
    }

}
