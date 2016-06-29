package cn.ucai.fulicenter.bean;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by ucai001 on 2016/3/1.
 */
public class CartBean implements Serializable {

    /**
     * id : 7672
     * userName : 7672
     * goodsId : 7672
     * count : 2
     * checked : true
     */

    private int id;
    private String userName;
    private int goodsId;
    private GoodDetailsBean goods;
    private int count;
    @JsonProperty("isChecked")
    private boolean isChecked;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public GoodDetailsBean getGoods() {
        return goods;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setGoods(GoodDetailsBean goods) {
        this.goods = goods;
    }

    @JsonIgnore
    public boolean isChecked() {
        return isChecked;
    }

    public CartBean() {
    }

    public CartBean(int id, String userName, int goodsId, int count, boolean isChecked) {
        this.id = id;
        this.userName = userName;
        this.goodsId = goodsId;
        this.count = count;
        this.isChecked = isChecked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartBean cartBean = (CartBean) o;

        return goodsId == cartBean.goodsId;

    }

    @Override
    public int hashCode() {
        return goodsId;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", goodsId=" + goodsId +
                ", goods=" + goods +
                ", count=" + count +
                ", isChecked=" + isChecked +
                '}';
    }
}
