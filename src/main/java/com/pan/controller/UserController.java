package com.pan.controller;

import com.alibaba.fastjson.JSONObject;
import com.pan.entity.ResultEntity;
import com.pan.entity.SysRoleEntity;
import com.pan.entity.SysUserEntity;
import com.pan.entity.SysUserRoleEntity;
import com.pan.enums.ResultEnum;
import com.pan.service.SysRoleService;
import com.pan.service.SysUserRoleService;
import com.pan.service.SysUserService;
import com.pan.util.ShiroUtils;
import com.pan.util.StringUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户信息
 * @author tangpan
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 跳转user页面
     * @param model
     * @return
     */
    @RequestMapping("/user")
    public String user(Model model){
        List<SysRoleEntity> sysRoleEntities = sysRoleService.findALL();
        model.addAttribute("sysRoleEntities", sysRoleEntities);
        return "user/sysUser";
    }

    /**
     *
     * @param request
     * @param entity
     * @return
     */
    @RequestMapping("/userList")
    @ResponseBody
    public JSONObject userList(HttpServletRequest request, SysUserEntity entity){
        //拼接参数
        Map<String,Object> map = new HashMap<>();
        map.put("username", entity.getUsername());
        map.put("roleId", request.getParameter("roleId"));

        map.put("limit", entity.getLimit());
        map.put("offset", entity.getOffset());
        //列表
        List<SysUserEntity> entities = sysUserService.findByParames(map);
        //总数
        int total = sysUserService.countByParames(map);

        //封装数据
        JSONObject object = new JSONObject();
        object.put("data", entities);
        object.put("total", total);
        return object;
    }

    /**
     * 添加账号
     * @return
     */
    @RequestMapping("add")
    public String addUser(Model model){
        List<SysRoleEntity> sysRoleEntityList = sysRoleService.findALL();
        model.addAttribute("sysRoleEntityList", sysRoleEntityList);
        return "user/add";
    }

    /**
     * 判断用户是否存在
     * @return
     */
    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    @ResponseBody
    public Boolean exit(String username){
        //根据用户名查询是否有该用户
        Map map = new HashMap();
        map.put("username", username);
        int i = sysUserService.countByParames(map);
        //存在返回false  否则返回true
        if(i > 0){
            return false;
        }else{
            return true;
        }
    }


    /**
     * 保存用户
     * @param entity 实体对象
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity save(SysUserEntity entity, HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        try {
            //密码加密
            entity.setPassword(ShiroUtils.sha256(entity.getPassword(), StringUtils.isNotEmpty(entity.getSalt()) ? entity.getPassword() : ""));
            //用户保存
            int insert = sysUserService.insert(entity);
            if(insert > 0){
                //获取勾选的角色
                String roleIds = request.getParameter("roleIds");
                if(StringUtils.isNotEmpty(roleIds)){
                    String[] split = roleIds.split(",");
                    List<SysUserRoleEntity> sysUserRoleEntityList = new ArrayList<>();
                    for (String roleId : split) {
                        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
                        sysUserRoleEntity.setRoleId(Long.parseLong(roleId));
                        sysUserRoleEntity.setUserId(entity.getId());
                        sysUserRoleEntityList.add(sysUserRoleEntity);
                    }
                    //用户角色关联保存
                    sysUserRoleService.batchInsert(sysUserRoleEntityList);
                }
            }
            resultEntity.setCode(ResultEnum.SAVESUCCESS.getCode());
            resultEntity.setMessage(ResultEnum.SAVESUCCESS.getMessage());
        } catch (Exception e) {
            resultEntity.setCode(ResultEnum.SAVEERROR.getCode());
            resultEntity.setMessage(ResultEnum.SAVEERROR.getMessage());
            e.printStackTrace();
        }
        return resultEntity;
    }

    /**
     * 跳转修改用户
     * @param id 用户id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") long id){
        //用户信息
        SysUserEntity sysUserEntity = sysUserService.findById(id);
        model.addAttribute("sysUserEntity",sysUserEntity);
        //角色信息
        List<SysRoleEntity> sysRoleEntities = sysRoleService.findALL();
        model.addAttribute("sysRoleEntities", sysRoleEntities);
        //拼接用户信息参数
        Map<String, Object> map = new HashMap<>();
        map.put("userId", id);
        List<SysUserRoleEntity> sysUserRoleEntityList = sysUserRoleService.findByParames(map);
        if(sysUserRoleEntityList != null && sysUserRoleEntityList.size() > 0){
            List<Long> roleIds = sysUserRoleEntityList.stream().map(entity -> {
                return entity.getRoleId();
            }).collect(Collectors.toList());
            model.addAttribute("roleIds",roleIds);
        }else {
            model.addAttribute("roleIds",new ArrayList<Long>());
        }
        return "user/edit";
    }

    /**
     * 修改
     * @param sysUserEntity 参数
     * @param request
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultEntity update(SysUserEntity sysUserEntity, HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        try {
            //修改用户信息
            int update = sysUserService.update(sysUserEntity);
            if(update > 0){
                //删除用户角色信息
                Map<String,Object> map = new HashMap<>();
                map.put("userId", sysUserEntity.getId());
                sysUserRoleService.deleteParame(map);

                //获取勾选的角色
                String roleIds = request.getParameter("roleIds");
                if(StringUtils.isNotEmpty(roleIds)){
                    String[] split = roleIds.split(",");
                    List<SysUserRoleEntity> sysUserRoleEntityList = new ArrayList<>();
                    //拼接关联信息
                    for (String roleId : split) {
                        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
                        sysUserRoleEntity.setRoleId(Long.parseLong(roleId));
                        sysUserRoleEntity.setUserId(sysUserEntity.getId());
                        sysUserRoleEntityList.add(sysUserRoleEntity);
                    }
                    //用户角色关联保存
                    sysUserRoleService.batchInsert(sysUserRoleEntityList);
                }
            }
            resultEntity.setCode(ResultEnum.SAVESUCCESS.getCode());
            resultEntity.setMessage(ResultEnum.SAVESUCCESS.getMessage());
        } catch (Exception e) {
            resultEntity.setCode(ResultEnum.SAVEERROR.getCode());
            resultEntity.setMessage(ResultEnum.SAVEERROR.getMessage());
            e.printStackTrace();
        }
        return resultEntity;
    }

    /**
     * 删除信息
     * @param id 用户信息
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity removeUser(@RequestParam("id") long id){
        ResultEntity resultEntity = new ResultEntity();
        try {
            //删除用户信息
            int i = sysUserService.deleteById(id);
            if(i > 0){
                //删除用户关联角色信息
                Map<String,Object> map = new HashMap<>();
                map.put("userId",id);
                sysUserRoleService.deleteParame(map);
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

    /**
     * 修改密码
     * @param model
     * @return
     */
    @RequestMapping("resetPwd/{id}")
    public String resetPwd(Model model, @PathVariable("id") long id){
        model.addAttribute("id", id);
        return "user/resetPwd";
    }

    /**
     * 修改密码保存
     * @param sysUserEntity
     * @return
     */
    @RequestMapping(value = "/updateResetPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity updateResetPwd(SysUserEntity sysUserEntity){
        ResultEntity resultEntity = new ResultEntity();
        sysUserEntity.setPassword(ShiroUtils.sha256(sysUserEntity.getPassword(), sysUserEntity.getSalt()));
        try {
            sysUserService.update(sysUserEntity);
            resultEntity.setCode(ResultEnum.SAVESUCCESS.getCode());
            resultEntity.setMessage(ResultEnum.SAVESUCCESS.getMessage());
        } catch (Exception e) {
            resultEntity.setCode(ResultEnum.SAVEERROR.getCode());
            resultEntity.setMessage(ResultEnum.SAVEERROR.getMessage());
            e.printStackTrace();
        }
        return resultEntity;
    }

}
