package com.pan.dao;

import com.pan.entity.TProjectFilePathInfoEntity;
import com.pan.query.TProjectFilePathInfoQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 服务器文件配置
 * @author tangpan
 */
@Mapper
public interface TProjectFilePathInfoDao extends BaseDao<TProjectFilePathInfoEntity> {

    /**
     * 查询文件服务器列表
     * @param map
     * @return
     */
    List<TProjectFilePathInfoQuery> findQueryByParames(Map<String,Object> map);
}
