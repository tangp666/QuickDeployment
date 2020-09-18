package com.pan.entity;

import javax.persistence.Id;

/**
 * 通用实体
 * @author tangpan
 */
public class BaseEntity {
    //序列
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    //当前页条数
    private int limit;
    //当前起始位置
    private int offset;
    //排序字段
    private String sort;
    //排序方式 asc desc
    private String order;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
