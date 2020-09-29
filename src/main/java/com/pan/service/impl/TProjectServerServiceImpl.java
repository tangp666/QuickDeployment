package com.pan.service.impl;

import com.pan.dao.TProjectServerDao;
import com.pan.entity.TProjectServerEntity;
import com.pan.service.TProjectServerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 实现类
 * @author tangpan
 */
@Service("tProjectServerService")
public class TProjectServerServiceImpl implements TProjectServerService {

    @Resource
    private TProjectServerDao tProjectServerDao;

    @Override
    public TProjectServerEntity findById(long id) {
        return tProjectServerDao.findById(id);
    }

    @Override
    public List<TProjectServerEntity> findALL() {
        return tProjectServerDao.findALL();
    }

    @Override
    public int countAll() {
        return tProjectServerDao.countAll();
    }

    @Override
    public List<TProjectServerEntity> findByParames(Map<String, Object> map) {
        return tProjectServerDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return tProjectServerDao.countByParames(map);
    }

    @Override
    public int insert(TProjectServerEntity tProjectServerEntity) {
        return tProjectServerDao.insert(tProjectServerEntity);
    }

    @Override
    public int update(TProjectServerEntity tProjectServerEntity) {
        return tProjectServerDao.update(tProjectServerEntity);
    }

    @Override
    public int deleteById(long id) {
        return tProjectServerDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return tProjectServerDao.deleteBatch(ids);
    }

    @Override
    public int deleteByParame(Map<String, Object> map) {
        return tProjectServerDao.deleteByParame(map);
    }
}
