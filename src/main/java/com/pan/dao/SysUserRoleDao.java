package com.pan.dao;

import com.pan.entity.SysUserEntity;
import com.pan.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户角色关联信息
 * @author tangpan
 */
@Mapper
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<SysUserRoleEntity> list);

    /**
     * 根据参数删除
     * @param map
     * @return
     */
    int deleteByParame(Map<String,Object> map);

}
