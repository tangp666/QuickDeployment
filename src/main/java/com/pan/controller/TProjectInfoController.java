package com.pan.controller;

import com.alibaba.fastjson.JSONObject;
import com.pan.entity.ResultEntity;
import com.pan.entity.TProjectInfoEntity;
import com.pan.enums.ResultEnum;
import com.pan.service.TProjectInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目信息
 * @author tangpan
 */
@Controller
@RequestMapping("/project")
public class TProjectInfoController extends BaseController {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(TProjectInfoController.class);

    @Autowired
    private TProjectInfoService tProjectInfoService;

    /**
     * 跳转项目信息页面
     * @param model
     * @return
     */
    @RequestMapping("/project")
    public String project(Model model){
        return "/project/project";
    }

    /**
     * 项目列表信息
     * @param tProjectInfoEntity
     * @return
     */
    @RequestMapping("/projectList")
    @ResponseBody
    public ResultEntity projectList(TProjectInfoEntity tProjectInfoEntity){
        //拼接
        Map<String,Object> map = new HashMap<>();
        map.put("projectName", tProjectInfoEntity.getProjectName());
        //分页
        map.put("limit", tProjectInfoEntity.getLimit());
        map.put("offset", tProjectInfoEntity.getOffset());

        ResultEntity resultEntity = new ResultEntity();
        try {
            //列表
            List<TProjectInfoEntity> tProjectInfoEntities = tProjectInfoService.findByParames(map);
            //数量
            int total = tProjectInfoService.countByParames(map);
            JSONObject object = new JSONObject();
            object.put("data", tProjectInfoEntities);
            object.put("total", total);
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
     * 添加项目 跳转页面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String addProject(Model model){
        return "project/add";
    }

    /**
     * 判断项目是否存在
     * @param tProjectInfoEntity
     * @return
     */
    @RequestMapping("exit")
    @ResponseBody
    public boolean exit(TProjectInfoEntity tProjectInfoEntity) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectName",tProjectInfoEntity.getProjectName());
        int i = tProjectInfoService.countByParames(map);
        if (i > 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 保存
     * @param request
     * @param tProjectInfoEntity 项目实体对象
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity save(HttpServletRequest request, TProjectInfoEntity tProjectInfoEntity){
        ResultEntity resultEntity = new ResultEntity();
        try {
            //保存
            int insert = tProjectInfoService.insert(tProjectInfoEntity);
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
     *  跳转修改页面
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String editProject(Model model, @PathVariable("id") long id){
        //当前项目信息
        TProjectInfoEntity tProjectInfoEntity = tProjectInfoService.findById(id);
        model.addAttribute("tProjectInfoEntity",tProjectInfoEntity);
        return "project/edit";
    }

    /**
     * 修改项目信息
     * @param request
     * @param tProjectInfoEntity 项目信息
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity updateProject(HttpServletRequest request, TProjectInfoEntity tProjectInfoEntity){
        ResultEntity resultEntity = new ResultEntity();
        try {
            //修改
            int update = tProjectInfoService.update(tProjectInfoEntity);
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
     * 删除项目xinxi
     * @param id 项目id
     * @return
     */
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity remove(@RequestParam("id") long id){
        ResultEntity resultEntity = new ResultEntity();
        try {
            //删除
            int i = tProjectInfoService.deleteById(id);
            resultEntity.setCode(ResultEnum.DELETESUCCESS.getCode());
            resultEntity.setMessage(ResultEnum.DELETESUCCESS.getMessage());
        } catch (Exception e) {
            resultEntity.setCode(ResultEnum.DELETEERROR.getCode());
            resultEntity.setMessage(ResultEnum.DELETEERROR.getMessage());
            e.printStackTrace();
        }
        return resultEntity;
    }

}
