package com.pan.service;

import com.pan.entity.TProjectFilePathInfoEntity;
import com.pan.query.TProjectFilePathInfoQuery;

import java.util.List;
import java.util.Map;

/**
 * 项目、服务、文件关系
 * @author tangpan
 */
public interface TProjectFilePathInfoServer extends BaseService<TProjectFilePathInfoEntity> {

    /**
     * 查询文件服务器列表
     * @param map
     * @return
     */
    List<TProjectFilePathInfoQuery> findQueryByParames(Map<String,Object> map);

}
