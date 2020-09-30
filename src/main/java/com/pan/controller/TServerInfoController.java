package com.pan.controller;

import com.alibaba.fastjson.JSONObject;
import com.pan.entity.ResultEntity;
import com.pan.entity.TProjectInfoEntity;
import com.pan.entity.TProjectServerEntity;
import com.pan.entity.TServerInfoEntity;
import com.pan.enums.ResultEnum;
import com.pan.query.Tree;
import com.pan.service.TProjectInfoService;
import com.pan.service.TProjectServerService;
import com.pan.service.TServerInfoService;
import com.pan.util.ShiroUtils;
import com.pan.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务器配置实现
 */
@Controller
@RequestMapping("server")
public class TServerInfoController extends BaseController{

    @Autowired
    private TServerInfoService tServerInfoService;

    @Autowired
    private TProjectServerService tProjectServerService;

    @Autowired
    private TProjectInfoService tProjectInfoService;
    /**
     * 列表
     * @param model
     * @return
     */
    @RequestMapping("server")
    public String list(Model model){
        return "server/server";
    }

    /**
     * 服务器列表
     * @param request
     * @param tServerInfoEntity 服务器参数
     * @return ResultEntity
     */
    @RequestMapping("serverList")
    @ResponseBody
    public ResultEntity serverList(HttpServletRequest request, TServerInfoEntity tServerInfoEntity){
        //拼接参数
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(tServerInfoEntity.getServerName())){
            map.put("serverName", tServerInfoEntity.getServerName());
        }
        if(StringUtils.isNotEmpty(tServerInfoEntity.getServerAddress())){
            map.put("serverAddress", tServerInfoEntity.getServerAddress());
        }
        if(tServerInfoEntity.getLimit() != 0 && tServerInfoEntity.getOffset() != 0){
            map.put("limit", tServerInfoEntity.getLimit());
            map.put("offset", tServerInfoEntity.getOffset());
        }
        //返回对象
        ResultEntity resultEntity = new ResultEntity();
        try {
            //列表
            List<TServerInfoEntity> tServerInfoEntities = tServerInfoService.findByParames(map);
            //数量
            int total = tServerInfoService.countByParames(map);
            JSONObject object = new JSONObject();
            object.put("data", tServerInfoEntities);
            object.put("total", total);
            //封装
            resultEntity.setCode(ResultEnum.SUCCESS.getCode());
            resultEntity.setMessage(ResultEnum.SUCCESS.getMessage());
            resultEntity.setData(object);
        } catch (Exception e) {
            resultEntity.setCode(ResultEnum.EXCEPTION.getCode());
            resultEntity.setMessage(ResultEnum.EXCEPTION.getMessage());
            e.printStackTrace();
        }
        return resultEntity;
    }

    /**
     * 跳转添加页面
     * @param model
     * @return
     */
    @RequestMapping("add")
    public String add(Model model){
        //项目列表
        List<TProjectInfoEntity> tProjectInfoEntities = tProjectInfoService.findALL();
        model.addAttribute("tProjectInfoEntities",tProjectInfoEntities);
        return "server/add";
    }

    /**
     * 判断服务器名称、地址是否存在
     * @param tServerInfoEntity 判断参数
     * @return
     */
    @RequestMapping("exit")
    @ResponseBody
    public boolean exit(TServerInfoEntity tServerInfoEntity){
        //拼接参数
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(tServerInfoEntity.getServerName())){
            map.put("serverName", tServerInfoEntity.getServerName());
        }
        if(StringUtils.isNotEmpty(tServerInfoEntity.getServerAddress())){
            map.put("serverAddress", tServerInfoEntity.getServerAddress());
        }
        try {
            //判断异常返回false
            int i = tServerInfoService.countByParames(map);
            if(i > 0){
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存服务器信息、 保存服务器和项目关联信息
     * @param request
     * @param tServerInfoEntity
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity save(HttpServletRequest request, TServerInfoEntity tServerInfoEntity){
        //返回对象
        ResultEntity resultEntity = new ResultEntity();
        try {
            //服务器密码加密
            tServerInfoEntity.setServerPassword(ShiroUtils.sha256(tServerInfoEntity.getServerPassword(),""));
            tServerInfoEntity.setCreateTime(new Date());
            tServerInfoEntity.setCreateUserId(ShiroUtils.getUserId());
            int insert = tServerInfoService.insert(tServerInfoEntity);
            //保存成功 储存关系信息
            if(insert > 0){
                //获取项目信息 并保存关联信息
                String projectId = request.getParameter("projectId");
                if(StringUtils.isNotEmpty(projectId)){
                    TProjectServerEntity tProjectServerEntity = new TProjectServerEntity();
                    tProjectServerEntity.setProjectId(Long.parseLong(projectId));
                    tProjectServerEntity.setServerId(tServerInfoEntity.getId());
                    tProjectServerService.insert(tProjectServerEntity);
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
     * 跳转修改页面
     * @param model
     * @param id 服务器id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        //服务器信息
        TServerInfoEntity tServerInfoEntity = tServerInfoService.findById(id);
        model.addAttribute("tServerInfoEntity", tServerInfoEntity);

        //项目列表
        List<TProjectInfoEntity> tProjectInfoEntities = tProjectInfoService.findALL();
        model.addAttribute("tProjectInfoEntities",tProjectInfoEntities);

        Map<String,Object> map = new HashMap<>();
        map.put("serverId",id);
        List<TProjectServerEntity> tProjectServerEntities = tProjectServerService.findByParames(map);
        TProjectServerEntity tProjectServerEntity = new TProjectServerEntity();
        if(tProjectServerEntities != null && tProjectServerEntities.size() > 0){
            tProjectServerEntity = tProjectServerEntities.get(0);
        }
        model.addAttribute("tProjectServerEntity",tProjectServerEntity);
        return "server/edit";
    }

    /**
     * 修改服务器信息
     * @param request
     * @param tServerInfoEntity 服务器参数
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity update(HttpServletRequest request, TServerInfoEntity tServerInfoEntity){
        //返回对象
        ResultEntity resultEntity = new ResultEntity();
        try {
            //判断密码是否修改
            TServerInfoEntity oldEntity = tServerInfoService.findById(tServerInfoEntity.getId());
            //如果密码相同则不加密 不同即为修改过密码 需要加密
            if(!oldEntity.getServerPassword().equals(tServerInfoEntity.getServerPassword())){
                tServerInfoEntity.setServerPassword(ShiroUtils.sha256(tServerInfoEntity.getServerPassword(),""));
            }
            int update = tServerInfoService.update(tServerInfoEntity);
            //保存成功 储存关系信息
            if(update > 0){
                //删除服务器保存的信息
                Map<String,Object> map = new HashMap<>();
                map.put("serverId", tServerInfoEntity.getId());
                tProjectServerService.deleteByParame(map);
                //获取项目信息 并保存关联信息
                String projectId = request.getParameter("projectId");
                if(StringUtils.isNotEmpty(projectId)){
                    TProjectServerEntity tProjectServerEntity = new TProjectServerEntity();
                    tProjectServerEntity.setProjectId(Long.parseLong(projectId));
                    tProjectServerEntity.setServerId(tServerInfoEntity.getId());
                    tProjectServerService.insert(tProjectServerEntity);
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
     * 删除服务器信息
     * @param id 服务器id
     * @return
     */
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity remove(@RequestParam("id") long id){
        ResultEntity resultEntity = new ResultEntity();

        try {
            //删除服务器信息
            int i = tServerInfoService.deleteById(id);
            if(i > 0){
                //删除服务器保存的信息
                Map<String,Object> map = new HashMap<>();
                map.put("serverId",id);
                tProjectServerService.deleteByParame(map);
            }
            resultEntity.setCode(ResultEnum.DELETESUCCESS.getCode());
            resultEntity.setMessage(ResultEnum.DELETESUCCESS.getMessage());
        } catch (Exception e) {
            resultEntity.setCode(ResultEnum.DELETEERROR.getCode());
            resultEntity.setMessage(ResultEnum.DELETEERROR.getMessage());
            e.printStackTrace();
        }
        return resultEntity;
    }

    /**
     * 所有的服务器列表
     * @return
     */
    @RequestMapping("serverTree")
    @ResponseBody
    public Tree<TServerInfoEntity> treeServerData(@RequestParam("projectId") long projectId){
        return tServerInfoService.treeServerData(projectId);
    }

}
