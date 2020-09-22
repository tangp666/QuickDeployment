package com.pan.service.impl;

import com.pan.dao.SysRoleDao;
import com.pan.entity.SysRoleEntity;
import com.pan.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色管理实现类
 * @author tangpan
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public SysRoleEntity findById(long id) {
        return sysRoleDao.findById(id);
    }

    @Override
    public List<SysRoleEntity> findALL() {
        return sysRoleDao.findALL();
    }

    @Override
    public int countAll() {
        return sysRoleDao.countAll();
    }

    @Override
    public List<SysRoleEntity> findByParames(Map<String, Object> map) {
        return sysRoleDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return sysRoleDao.countByParames(map);
    }

    @Override
    public int insert(SysRoleEntity sysRoleEntity) {
        return sysRoleDao.insert(sysRoleEntity);
    }

    @Override
    public int update(SysRoleEntity sysRoleEntity) {
        return sysRoleDao.update(sysRoleEntity);
    }

    @Override
    public int deleteById(long id) {
        return sysRoleDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return sysRoleDao.deleteBatch(ids);
    }

    @Override
    public int checkAdmin(Map<String, Object> map) {
        return sysRoleDao.checkAdmin(map);
    }
}
