package com.ego.item.pojo;

import java.util.List;

/**
 * @Auther: liuxw
 * @Date: 2019/8/15
 * @Description: com.ego.item.pojo
 * @version: 1.0
 */
public class ParamData {
    private String group;
    private List<SmallParams> params;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<SmallParams> getParams() {
        return params;
    }

    public void setParams(List<SmallParams> params) {
        this.params = params;
    }

    public ParamData() {
    }

    public ParamData(String group, List<SmallParams> params) {
        this.group = group;
        this.params = params;
    }
}
