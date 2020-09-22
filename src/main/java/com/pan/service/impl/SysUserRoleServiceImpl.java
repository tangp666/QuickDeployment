package com.pan.service.impl;

import com.pan.dao.SysUserRoleDao;
import com.pan.entity.SysUserRoleEntity;
import com.pan.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户角色信息
 * @author tangpan
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public SysUserRoleEntity findById(long id) {
        return sysUserRoleDao.findById(id);
    }

    @Override
    public List<SysUserRoleEntity> findALL() {
        return sysUserRoleDao.findALL();
    }

    @Override
    public int countAll() {
        return sysUserRoleDao.countAll();
    }

    @Override
    public List<SysUserRoleEntity> findByParames(Map<String, Object> map) {
        return sysUserRoleDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return sysUserRoleDao.countByParames(map);
    }

    @Override
    public int insert(SysUserRoleEntity sysUserRoleEntity) {
        return sysUserRoleDao.insert(sysUserRoleEntity);
    }

    @Override
    public int update(SysUserRoleEntity sysUserRoleEntity) {
        return sysUserRoleDao.update(sysUserRoleEntity);
    }

    @Override
    public int deleteById(long id) {
        return sysUserRoleDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return sysUserRoleDao.deleteBatch(ids);
    }

    @Override
    public int batchInsert(List<SysUserRoleEntity> list) {
        return sysUserRoleDao.batchInsert(list);
    }

    @Override
    public int deleteParame(Map<String, Object> map) {
        return sysUserRoleDao.deleteByParame(map);
    }
}
