package com.pan.service.impl;

import com.pan.dao.TProjectFilePathInfoDao;
import com.pan.entity.TProjectFilePathInfoEntity;
import com.pan.service.TProjectFilePathInfoServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 项目、服务、文件关系 实现
 * @author tangpan
 */
@Service("tProjectFilePathInfoServer")
public class TProjectFilePathInfoServerImpl implements TProjectFilePathInfoServer {

    @Resource
    private TProjectFilePathInfoDao tServerFilePathInfoDao;

    @Override
    public TProjectFilePathInfoEntity findById(long id) {
        return tServerFilePathInfoDao.findById(id);
    }

    @Override
    public List<TProjectFilePathInfoEntity> findALL() {
        return tServerFilePathInfoDao.findALL();
    }

    @Override
    public int countAll() {
        return tServerFilePathInfoDao.countAll();
    }

    @Override
    public List<TProjectFilePathInfoEntity> findByParames(Map<String, Object> map) {
        return tServerFilePathInfoDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return tServerFilePathInfoDao.countByParames(map);
    }

    @Override
    public int insert(TProjectFilePathInfoEntity tServerFilePathInfoEntity) {
        return tServerFilePathInfoDao.insert(tServerFilePathInfoEntity);
    }

    @Override
    public int update(TProjectFilePathInfoEntity tServerFilePathInfoEntity) {
        return tServerFilePathInfoDao.update(tServerFilePathInfoEntity);
    }

    @Override
    public int deleteById(long id) {
        return tServerFilePathInfoDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return tServerFilePathInfoDao.deleteBatch(ids);
    }
}
