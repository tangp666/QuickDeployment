package com.pan.service.impl;

import com.pan.dao.BaseDao;
import com.pan.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础查询
 * @param <T> 实体对象
 * @author tangpan
 */
@Service("baseService")
public class BaseServiceImpl<T> implements BaseService<T> {

    private BaseDao<T> baseDao;
    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public T findById(long id) {
        return baseDao.findById(id);
    }

    @Override
    public List<T> findALL() {
        return baseDao.findALL();
    }

    @Override
    public int countAll() {
        return baseDao.countAll();
    }

    @Override
    public List<T> findByParames(Map<String, Object> map) {
        return baseDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return baseDao.countByParames(map);
    }

    @Override
    public int insert(T t) {
        return baseDao.insert(t);
    }

    @Override
    public int update(T t) {
        return baseDao.update(t);
    }

    @Override
    public int deleteById(long id) {
        return baseDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return baseDao.deleteBatch(ids);
    }
}
