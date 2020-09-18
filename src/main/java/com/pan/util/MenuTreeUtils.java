package com.pan.util;

import com.pan.query.SysMenuQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树节点
 * @author tangpan
 */
public class MenuTreeUtils {

    //树节点集合
    private List<SysMenuQuery> menuList = new ArrayList<SysMenuQuery>();

    public MenuTreeUtils(List<SysMenuQuery> menuList) {
        this.menuList = menuList;
    }

    //建立树形结构
    public List<SysMenuQuery> builTree(){
        List<SysMenuQuery> treeMenus = new  ArrayList<>();
        for(SysMenuQuery menuNode : getRootNode()) {
            menuNode = buildChilTree(menuNode);
            treeMenus.add(menuNode);
        }
        return treeMenus;
    }

    //递归，建立子树形结构
    private SysMenuQuery buildChilTree(SysMenuQuery pNode){
        List<SysMenuQuery> chilMenus =new  ArrayList<SysMenuQuery>();
        for(SysMenuQuery menuNode : menuList) {
            if(menuNode.getParentId().equals(pNode.getId())) {
                chilMenus.add(buildChilTree(menuNode));
            }
        }
        pNode.setChildMenu(chilMenus);
        return pNode;
    }

    //获取根节点
    private List<SysMenuQuery> getRootNode() {
        List<SysMenuQuery> rootMenuLists =new  ArrayList<>();
        for(SysMenuQuery menuNode : menuList) {
            //树节点中父节点是long类型  常量定义的是int类型  即用string类型比较
            if(String.valueOf(menuNode.getParentId()).equals(String.valueOf(MacroelementUtils.ZERO))) {
                rootMenuLists.add(menuNode);
            }
        }
        return rootMenuLists;
    }

}
