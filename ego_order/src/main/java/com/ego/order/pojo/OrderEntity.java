package com.ego.order.pojo;

import java.util.Arrays;

/**
 * @Auther: liuxw
 * @Date: 2019/8/20
 * @Description: com.ego.pojo
 * @version: 1.0
 */
public class OrderEntity {
    private String id;
    private String [] images;
    private String sellPoint;
    private String title;
    private long price;
    private int num;
    private boolean enough;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isEnough() {
        return enough;
    }

    public void setEnough(boolean enough) {
        this.enough = enough;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + id + '\'' +
                ", images=" + Arrays.toString(images) +
                ", sellPoint='" + sellPoint + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", enough=" + enough +
                '}';
    }
}
