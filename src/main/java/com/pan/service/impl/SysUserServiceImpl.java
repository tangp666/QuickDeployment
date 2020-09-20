package com.pan.service.impl;

import com.pan.dao.SysUserDao;
import com.pan.entity.SysUserEntity;
import com.pan.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Resource
    public SysUserDao sysUserDao;

    @Override
    public SysUserEntity findById(long id) {
        return sysUserDao.findById(id);
    }

    @Override
    public List<SysUserEntity> findALL() {
        return sysUserDao.findALL();
    }

    @Override
    public int countAll() {
        return sysUserDao.countAll();
    }

    @Override
    public List<SysUserEntity> findByParames(Map<String, Object> map) {
        return sysUserDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return sysUserDao.countByParames(map);
    }

    @Override
    public int insert(SysUserEntity t) {
        return sysUserDao.insert(t);
    }

    @Override
    public int update(SysUserEntity t) {
        return sysUserDao.update(t);
    }

    @Override
    public int deleteById(long id) {
        return sysUserDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return sysUserDao.deleteBatch(ids);
    }
}
