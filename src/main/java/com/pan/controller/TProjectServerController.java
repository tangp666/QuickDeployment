package com.pan.controller;

import com.alibaba.fastjson.JSONObject;
import com.pan.entity.ResultEntity;
import com.pan.entity.TProjectServerEntity;
import com.pan.entity.TServerInfoEntity;
import com.pan.enums.ResultEnum;
import com.pan.query.TServerInfoQuery;
import com.pan.service.TProjectServerService;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务器和项目关系
 * @author tangpan
 */
@Controller
@RequestMapping("tProjectServer")
public class TProjectServerController {

    @Autowired
    private TProjectServerService tProjectServerService;

    /**
     * 项目关联的服务器列表信息
     * @param request
     * @param tProjectServerEntity 服务器参数
     * @return
     */
    @RequestMapping("serverList")
    @ResponseBody
    public ResultEntity TServerList(HttpServletRequest request, TProjectServerEntity tProjectServerEntity){
        //返回对象
        ResultEntity resultEntity = new ResultEntity();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("projectId", tProjectServerEntity.getProjectId());
            List<TServerInfoQuery> tServerInfoLists = tProjectServerService.findTServerInfoLists(map);
            JSONObject object = new JSONObject();
            object.put("data", tServerInfoLists);
            resultEntity.setData(object);
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
     * 保存项目和服务器关系
     * @param request
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity save(HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        try {
            long projectId = Long.parseLong(request.getParameter("projectId"));
            //通过项目id删除之前关联的服务器信息
            Map<String,Object> map = new HashMap<>();
            map.put("projectId", projectId);
            tProjectServerService.deleteByParame(map);
            //服务器列表
            String serverIds = request.getParameter("serverIds");
            if(serverIds != null){
                String[] split = serverIds.split(",");
                List<TProjectServerEntity> tProjectServerEntities = new ArrayList<>();
                //遍历选中的服务器
                for (String serverId : split) {
                    TProjectServerEntity tProjectServerEntity = new TProjectServerEntity();
                    tProjectServerEntity.setServerId(Long.parseLong(serverId));
                    tProjectServerEntity.setProjectId(projectId);
                    tProjectServerEntities.add(tProjectServerEntity);
                }
                //批量插入
                tProjectServerService.batchInsert(tProjectServerEntities);
            }
            resultEntity.setCode(ResultEnum.SAVESUCCESS.getCode());
            resultEntity.setMessage(ResultEnum.SAVESUCCESS.getMessage());
        } catch (NumberFormatException e) {
            resultEntity.setCode(ResultEnum.EXCEPTION.getCode());
            resultEntity.setMessage(ResultEnum.EXCEPTION.getMessage());
            e.printStackTrace();
        }
        return resultEntity;
    }


}
