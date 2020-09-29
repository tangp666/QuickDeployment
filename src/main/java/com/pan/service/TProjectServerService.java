package com.pan.service;

import com.pan.entity.TProjectServerEntity;

import java.util.Map;

/**
 * 项目和服务器关系
 * @author tangpan
 */
public interface TProjectServerService extends BaseService<TProjectServerEntity> {

    /**
     * 更加参数条件删除信息
     * @param map
     * @return
     */
    int deleteByParame(Map<String,Object> map);

}
