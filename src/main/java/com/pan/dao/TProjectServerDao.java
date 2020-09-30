package com.pan.dao;

import com.pan.entity.TProjectServerEntity;
import com.pan.entity.TServerInfoEntity;
import com.pan.query.TServerInfoQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目和服务关联关系
 * @author tangpan
 */
@Mapper
public interface TProjectServerDao extends BaseDao<TProjectServerEntity>{

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
    int batchInsert(@Param("list") List<TProjectServerEntity> list);
}
