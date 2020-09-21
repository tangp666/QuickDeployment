package com.pan.service.impl;

import com.pan.dao.SysMenuDao;
import com.pan.dao.SysRoleMenuDao;
import com.pan.entity.SysMenuEntity;
import com.pan.entity.SysRoleMenuEntity;
import com.pan.query.SysMenuQuery;
import com.pan.query.Tree;
import com.pan.service.SysMenuService;
import com.pan.util.BuildTreeUtils;
import com.pan.util.MenuTreeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单
 * @author tangpan
 */
@Service("sysMenuService")
public class SysMenuServiceIpml implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;

    @Resource
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public SysMenuEntity findById(long id) {
        return sysMenuDao.findById(id);
    }

    @Override
    public List<SysMenuEntity> findALL() {
        return sysMenuDao.findALL();
    }

    @Override
    public int countAll() {
        return sysMenuDao.countAll();
    }

    @Override
    public List<SysMenuEntity> findByParames(Map<String, Object> map) {
        return sysMenuDao.findByParames(map);
    }

    @Override
    public int countByParames(Map<String, Object> map) {
        return sysMenuDao.countByParames(map);
    }

    @Override
    public int insert(SysMenuEntity t) {
        return sysMenuDao.insert(t);
    }

    @Override
    public int update(SysMenuEntity t) {
        return sysMenuDao.update(t);
    }

    @Override
    public int deleteById(long id) {
        return sysMenuDao.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return sysMenuDao.deleteBatch(ids);
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
    public List<SysMenuQuery> findMenuTreeByUser(long userId, List<Integer> menuType) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("menuType", menuType);
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

    @Override
    public Tree<SysMenuEntity> getTree() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("sort", "order_num");
        map.put("order", "asc");
        List<SysMenuEntity> menus = sysMenuDao.findByParames(map);
        //判断树节点是否为空
        List<Tree<SysMenuEntity>> trees = new ArrayList<>();
        if(menus != null && menus.size() > 0){
            menus.stream().forEach(sysMenuEntity -> {
                Tree<SysMenuEntity> tree = new Tree<SysMenuEntity>();
                tree.setId(sysMenuEntity.getId().toString());
                tree.setParentId(sysMenuEntity.getParentId().toString());
                tree.setText(sysMenuEntity.getName());
                trees.add(tree);
            });
        }
        Tree<SysMenuEntity> build = BuildTreeUtils.build(trees);
        return build;
    }

    @Override
    public Tree<SysMenuEntity> getTree(long roleId) {
        // 根据roleId查询权限
        Map menuMap = new HashMap<String, Object>(16);
        menuMap.put("sort", "order_num");
        menuMap.put("order", "asc");
        //查询所有的菜单
        List<SysMenuEntity> menus = sysMenuDao.findByParames(menuMap);
        //选中的菜单
        Map roleMenuMap = new HashMap<String, Object>(16);
        roleMenuMap.put("roleId", roleId);
        List<Long> sysRoleMenuIds = sysRoleMenuDao.findMenusByParames(roleMenuMap);

        List<Tree<SysMenuEntity>> trees = new ArrayList<>();
        for (SysMenuEntity entity : menus) {
            Tree<SysMenuEntity> tree = new Tree<SysMenuEntity>();
            tree.setId(entity.getId().toString());
            tree.setParentId(entity.getParentId().toString());
            tree.setText(entity.getName());
            Map<String, Object> state = new HashMap<>(16);
            if (sysRoleMenuIds.contains(entity.getId())) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        Tree<SysMenuEntity> build = BuildTreeUtils.build(trees);
        return build;
    }
}
