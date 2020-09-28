package com.pan.service.impl;

import com.pan.dao.TProjectInfoDao;
import com.pan.entity.TProjectInfoEntity;
import com.pan.service.TProjectInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 项目信息实现
 * @author tangpan
 */
@Service("tProjectInfoService")
public class TProjectInfoServiceImpl implements TProjectInfoService {

    @Resource
    private TProjectInfoDao tProjectInfoDao;

    @Override
    public TProjectInfoEntity findById(long id) {
        return tProjectInfoDao.findById(id);
    }

    @Override
    public List<TProjectInfoEntity> findALL() {
        return tProjectInfoDao.findALL();
    }

    @Override
    public int countAll() {
        return tProjectInfoDao.countAll();
    }

    @Override
    public List<TProjectInfoEntity> findByParames(Map<String, Object> map) {
        return tProjectInfoDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return tProjectInfoDao.countByParames(map);
    }

    @Override
    public int insert(TProjectInfoEntity tProjectInfoEntity) {
        return tProjectInfoDao.insert(tProjectInfoEntity);
    }

    @Override
    public int update(TProjectInfoEntity tProjectInfoEntity) {
        return tProjectInfoDao.update(tProjectInfoEntity);
    }

    @Override
    public int deleteById(long id) {
        return tProjectInfoDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return tProjectInfoDao.deleteBatch(ids);
    }
}
