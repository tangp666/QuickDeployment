package com.pan.service.impl;

import com.pan.dao.BaseDao;
import com.pan.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaseServiceImpl<T> implements BaseService<T> {

    private BaseDao<T> baseDao;

    @Override
    public T findById(long id) {
        return baseDao.findById(id);
    }

    @Override
    public List<T> findALL() {
        return baseDao.findALL();
    }

    @Override
    public List<T> findByParames(Map<String, Object> map) {
        return baseDao.findByParames(map);
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
    public int deleteBatch(long[] ids) {
        return baseDao.deleteBatch(ids);
    }
}
