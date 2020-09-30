package com.pan.service;

import com.pan.entity.TProjectInfoEntity;

/**
 * 项目信息
 * @author tangpan
 */
public interface TProjectInfoService extends BaseService<TProjectInfoEntity> {

    /**
     * 构建项目
     * @param id 项目id
     */
    void rebuild(long id);


}
