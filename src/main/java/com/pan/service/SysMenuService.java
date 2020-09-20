package com.pan.service;

import com.pan.entity.SysMenuEntity;
import com.pan.query.SysMenuQuery;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单管理
 * @author tangpan
 */
public interface SysMenuService {

    /**
     * 根据id查询实体对象
     * @param id 主键id
     * @return 实体对象
     */
    SysMenuEntity findById(long id);

    /**
     * 查询所有实体对象
     * @return 实体对象集合
     */
    List<SysMenuEntity> findALL();

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
    List<SysMenuEntity> findByParames(Map<String,Object> map);

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
    int insert(SysMenuEntity t);

    /**
     * 修改
     * @param t 实体对象
     * @return
     */
    int update(SysMenuEntity t);

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

    /**
     * 通过用户获取系统菜单
     * @param userId 用户id
     * @return
     */
    List<SysMenuEntity> findMenuByUser(long userId);

    /**
     * 通过用户信息获取系统菜单树结构
     * @param userId 用户id
     * @param menuType 菜单类型  0,1,2 表示菜单和按钮  如果为空或为null对象查询所有
     * @return
     */
    List<SysMenuQuery> findMenuTreeByUser(long userId, List<Integer> menuType);

}
