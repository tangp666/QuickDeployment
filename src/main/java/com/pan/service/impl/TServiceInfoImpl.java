package com.pan.service.impl;

import com.pan.dao.TServerInfoDao;
import com.pan.entity.TServerInfoEntity;
import com.pan.service.TServerInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 实现
 * @author tangpan
 */
@Service("tServerInfoService")
public class TServiceInfoImpl implements TServerInfoService {

    @Resource
    private TServerInfoDao tServerInfoDao;

    @Override
    public TServerInfoEntity findById(long id) {
        return tServerInfoDao.findById(id);
    }

    @Override
    public List<TServerInfoEntity> findALL() {
        return tServerInfoDao.findALL();
    }

    @Override
    public int countAll() {
        return tServerInfoDao.countAll();
    }

    @Override
    public List<TServerInfoEntity> findByParames(Map<String, Object> map) {
        return tServerInfoDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return tServerInfoDao.countByParames(map);
    }

    @Override
    public int insert(TServerInfoEntity tServerInfoEntity) {
        return tServerInfoDao.insert(tServerInfoEntity);
    }

    @Override
    public int update(TServerInfoEntity tServerInfoEntity) {
        return tServerInfoDao.update(tServerInfoEntity);
    }

    @Override
    public int deleteById(long id) {
        return tServerInfoDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return tServerInfoDao.deleteBatch(ids);
    }
}
