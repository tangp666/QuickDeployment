package com.pan.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 菜单管理
 * @author tangpan
 */
@Table(name = "sys_menu")
public class SysMenuEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /* 父节点id */
    @Column(name = "parent_id")
    private Long parentId;
    /* 菜单名称 */
    @Column(name = "name")
    private String name;
    /* 菜单路径 */
    @Column(name = "url")
    private String url;
    /* shiro授权(多个用逗号分隔，如：user:list,user:create) */
    @Column(name = "perms")
    private String perms;
    /* 菜单类型   0：目录   1：菜单   2：按钮  */
    @Column(name = "type")
    private Integer type;
    /* 图标路径 */
    @Column(name = "icon")
    private String icon;
    /* 排序 */
    @Column(name = "order_num")
    private Integer orderNum;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}