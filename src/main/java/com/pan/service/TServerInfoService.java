package com.pan.service;

import com.pan.entity.TServerInfoEntity;
import com.pan.query.Tree;

/**
 * 服务器信息
 * @author tangpan
 */
public interface TServerInfoService extends BaseService<TServerInfoEntity> {

    /**
     * 服务器列表树
     * @param projectId 项目id
     * @return
     */
    Tree<TServerInfoEntity> treeServerData(long projectId);

}
