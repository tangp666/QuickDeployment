package com.pan.service.impl;

import com.pan.dao.SysUserDao;
import com.pan.entity.SysUserEntity;
import com.pan.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity> implements SysUserService {

    @Resource
    public SysUserDao sysUserDao;


}
