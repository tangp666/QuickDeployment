package com.pan.dao;

import com.pan.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户信息dao
 * @author tangpan
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity>{

    /**
     * 根据用户名查询用户
     */
    List<SysUserEntity> selectAccount(@Param("userName") String userName) ;

}