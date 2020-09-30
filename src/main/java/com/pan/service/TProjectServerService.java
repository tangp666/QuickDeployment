package com.pan.service;

import com.pan.entity.TProjectServerEntity;
import com.pan.query.TServerInfoQuery;

import java.util.List;
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

    /**
     * 查询服务器信息列表
     * @param map 服务器信息， 项目信息
     * @return
     */
    List<TServerInfoQuery> findTServerInfoLists(Map<String,Object> map);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(List<TProjectServerEntity> list);

}
