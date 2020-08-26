package com.ego.commons.pojo;

/**
 * @Auther: liuxw
 * @Date: 2019/8/6
 * @Description: com.ego.commons.pojo
 * @version: 1.0
 */
public class EgoResult {
    private Integer status;
    private Object data;
    private String msg;
    private static EgoResult er;


    public static EgoResult ok(){
        er = new EgoResult();
        er.setData(null);
        er.setStatus(200);
        er.setMsg("");
        return er;
    }
    public static EgoResult ok(Object data){
        er = new EgoResult();
        er.setData(data);
        er.setStatus(200);
        er.setMsg("");
        return er;
    }
    public static EgoResult error(String msg){
        er = new EgoResult();
        er.setMsg(msg);
        er.setData(null);
        er.setStatus(500);
        return er;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
