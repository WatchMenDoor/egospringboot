package com.ego.commons.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/6
 * @Description: com.ego.commons.pojo
 * @version: 1.0
 */
public class EasyUiDatagrid implements Serializable {
    private List<?> rows;
    private long total;

    public EasyUiDatagrid() {
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
