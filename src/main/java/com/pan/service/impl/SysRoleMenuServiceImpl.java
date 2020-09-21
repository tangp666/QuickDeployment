package com.pan.service.impl;

import com.pan.dao.SysRoleMenuDao;
import com.pan.entity.SysRoleMenuEntity;
import com.pan.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色菜单管理
 * @author tangpan
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Resource
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public SysRoleMenuEntity findById(long id) {
        return sysRoleMenuDao.findById(id);
    }

    @Override
    public List<SysRoleMenuEntity> findALL() {
        return sysRoleMenuDao.findALL();
    }

    @Override
    public int countAll() {
        return sysRoleMenuDao.countAll();
    }

    @Override
    public List<SysRoleMenuEntity> findByParames(Map<String, Object> map) {
        return sysRoleMenuDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return sysRoleMenuDao.countByParames(map);
    }

    @Override
    public int insert(SysRoleMenuEntity sysRoleMenuEntity) {
        return sysRoleMenuDao.insert(sysRoleMenuEntity);
    }

    @Override
    public int update(SysRoleMenuEntity sysRoleMenuEntity) {
        return sysRoleMenuDao.update(sysRoleMenuEntity);
    }

    @Override
    public int deleteById(long id) {
        return sysRoleMenuDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return sysRoleMenuDao.deleteBatch(ids);
    }

    @Override
    public int batchInsert(List<SysRoleMenuEntity> entities) {
        return sysRoleMenuDao.batchInsert(entities);
    }

    @Override
    public int deleteByParames(Map<String, Object> map) {
        return sysRoleMenuDao.deleteByParames(map);
    }
}
