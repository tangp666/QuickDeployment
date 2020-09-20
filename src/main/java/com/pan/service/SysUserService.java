package com.pan.service;

import com.pan.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户信息user
 * @author tangpan
 */
public interface SysUserService {

    /**
     * 根据id查询实体对象
     * @param id 主键id
     * @return 实体对象
     */
    SysUserEntity findById(long id);

    /**
     * 查询所有实体对象
     * @return 实体对象集合
     */
    List<SysUserEntity> findALL();

    /**
     * 查询所有数量
     * @return
     */
    int countAll();

    /**
     * 根据参数条件查询
     * @param map 参数条件
     * @return 对象集合
     */
    List<SysUserEntity> findByParames(Map<String,Object> map);

    /**
     * 根据条件查询数量
     * @param map
     * @return
     */
    int countByParames(Map<String,Object> map);
    /**
     * 插入
     * @param t 实体对象
     * @return
     */
    int insert(SysUserEntity t);

    /**
     * 修改
     * @param t 实体对象
     * @return
     */
    int update(SysUserEntity t);

    /**
     * 删除
     * @param id 主键id
     * @return
     */
    int deleteById(long id);

    /**
     * 批量删除
     * @param ids 主键id集合
     * @return
     */
    int deleteBatch(List<Long> ids);

}
