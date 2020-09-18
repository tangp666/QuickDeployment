package com.pan.service;

import java.util.List;
import java.util.Map;

/**
 * 基础service
 */
public interface BaseService<T> {

    /**
     * 根据id查询实体对象
     * @param id 主键id
     * @return 实体对象
     */
    T findById(long id);

    /**
     * 查询所有实体对象
     * @return 实体对象集合
     */
    List<T> findALL();

    /**
     * 根据参数条件查询
     * @param map 参数条件
     * @return 对象集合
     */
    List<T> findByParames(Map<String,Object> map);

    /**
     * 插入
     * @param t 实体对象
     * @return
     */
    int insert(T t);

    /**
     * 修改
     * @param t 实体对象
     * @return
     */
    int update(T t);

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
    int deleteBatch(long[] ids);
}
