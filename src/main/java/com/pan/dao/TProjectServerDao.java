package com.pan.dao;

import com.pan.entity.TProjectServerEntity;
import org.apache.ibatis.annotations.Mapper;

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
}
