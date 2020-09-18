package com.pan.service.impl;

import com.pan.dao.SysMenuDao;
import com.pan.entity.SysMenuEntity;
import com.pan.query.SysMenuQuery;
import com.pan.service.SysMenuService;
import com.pan.util.MenuTreeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("sysMenuService")
public class SysMenuServiceIpml extends BaseServiceImpl<SysMenuEntity> implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 查询所有的菜单
     * @return
     */
    @Override
    public List<SysMenuEntity> findAllMenu() {
        return sysMenuDao.findALL();
    }

    /**
     * 用户id查询所有的菜单
     * @param userId 用户id
     * @return
     */
    @Override
    public List<SysMenuEntity> findMenuByUser(long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        return sysMenuDao.findByParames(map);
    }

    /**
     * 通过用户信息获取系统菜单树结构
     * @param userId 用户id
     * @param menuType 菜单类型  1,2 表示菜单和按钮   类型用逗号隔开  如果为空或为null对象查询所有
     * @return
     */
    @Override
    public List<SysMenuQuery> findMenuTreeByUser(long userId, String menuType) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("type", menuType);
        List<SysMenuEntity> menuEntities = sysMenuDao.findByParames(map);

        List<SysMenuQuery> menuList = new ArrayList<>();
        //组装树结构
        if(menuEntities != null && menuEntities.size() > 0){
            List<SysMenuQuery> menuQueryList = menuEntities.stream().map(sysMenuEntity -> {
                SysMenuQuery query = new SysMenuQuery();
                BeanUtils.copyProperties(sysMenuEntity, query);
                return query;
            }).collect(Collectors.toList());
            /*创建树*/
            MenuTreeUtils menuTree =new MenuTreeUtils(menuQueryList);
            menuList = menuTree.builTree();
        }
        return menuList;
    }
}
