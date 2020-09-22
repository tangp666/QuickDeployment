package com.pan.dao;

import com.pan.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 角色管理dao
 * @author tangpan
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {

    /**
     * 判断是否是系统管理员
     * @param map
     * @return
     */
    int checkAdmin(Map<String,Object> map);

}
