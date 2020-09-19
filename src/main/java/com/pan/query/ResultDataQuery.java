package com.pan.query;

import java.io.Serializable;
import java.util.List;

/**
 * 返回对象实体对象
 * @author tangpan
 */
public class ResultDataQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    //查询总数量
    private int total;
    //当前返回列表集合
    private List<?> rows;

    public ResultDataQuery(int total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
