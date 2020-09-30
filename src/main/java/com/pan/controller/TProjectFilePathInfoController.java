package com.pan.controller;

import com.alibaba.fastjson.JSONObject;
import com.pan.entity.ResultEntity;
import com.pan.entity.TProjectFilePathInfoEntity;
import com.pan.enums.ResultEnum;
import com.pan.service.TProjectFilePathInfoServer;
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
 * 项目和项目文件关系
 */
@Controller
@RequestMapping("/filepath")
public class TProjectFilePathInfoController extends BaseController{

    @Autowired
    private TProjectFilePathInfoServer tProjectFilePathInfoServer;

    /**
     * 跳转文件管理
     * @param model
     * @return
     */
    @RequestMapping("/filepath")
    public String filepath(Model model){
        return "filepath/filepath";
    }

    /**
     * 文件列表
     * @param request
     * @param tProjectFilePathInfoEntity 文件实体参数
     * @return 列表
     */
    @RequestMapping("filepathList")
    @ResponseBody
    public ResultEntity filepathList(HttpServletRequest request, TProjectFilePathInfoEntity tProjectFilePathInfoEntity){
        ResultEntity resultEntity = new ResultEntity();
        try {
            //拼接参数
            Map<String,Object> map = new HashMap<>();
            if(StringUtils.isNotEmpty(tProjectFilePathInfoEntity.getFileName())){
                map.put("fileName",tProjectFilePathInfoEntity.getFileName());
            }
            if(tProjectFilePathInfoEntity.getFileType() > -1){
                map.put("fileType",tProjectFilePathInfoEntity.getFileType());
            }

            map.put("limit", tProjectFilePathInfoEntity.getLimit());
            map.put("offset", tProjectFilePathInfoEntity.getOffset());
            //列表
            List<TProjectFilePathInfoEntity> tProjectFilePathInfoEntities = tProjectFilePathInfoServer.findByParames(map);
            //总数
            int total = tProjectFilePathInfoServer.countByParames(map);
            //封装参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data",tProjectFilePathInfoEntities);
            jsonObject.put("total", total);
            resultEntity.setCode(ResultEnum.SUCCESS.getCode());
            resultEntity.setMessage(ResultEnum.SUCCESS.getMessage());
            resultEntity.setData(jsonObject);
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
    public String addFile(Model model){
        return "filepath/add";
    }

    /**
     * 保存
     * @param tProjectFilePathInfoEntity
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity save(TProjectFilePathInfoEntity tProjectFilePathInfoEntity){
        ResultEntity resultEntity = new ResultEntity();
        try {
            //储存
            tProjectFilePathInfoEntity.setCreateTime(new Date());
            tProjectFilePathInfoEntity.setCreateUserId(ShiroUtils.getUserId());
            tProjectFilePathInfoServer.insert(tProjectFilePathInfoEntity);
            //返回参数
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
     * @param id 主键id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        //文件信息
        TProjectFilePathInfoEntity tProjectFilePathInfoEntity = tProjectFilePathInfoServer.findById(id);
        model.addAttribute("tProjectFilePathInfoEntity",tProjectFilePathInfoEntity);
        return "filepath/edit";
    }

    /**
     * 修改
     * @param request
     * @param tProjectFilePathInfoEntity 文件信息
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultEntity update(HttpServletRequest request, TProjectFilePathInfoEntity tProjectFilePathInfoEntity){
        ResultEntity resultEntity = new ResultEntity();
        try {
            tProjectFilePathInfoServer.update(tProjectFilePathInfoEntity);
            //返回参数
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
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity remove(@RequestParam("id") long id){
        ResultEntity resultEntity = new ResultEntity();
        try {
            tProjectFilePathInfoServer.deleteById(id);
            //返回参数
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
