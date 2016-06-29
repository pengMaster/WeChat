package cn.ucai.fulicenter.bean;

import java.io.Serializable;

/**
 * Created by ucai001 on 2016/3/1.
 */
public class CollectBean implements Serializable {

    /**
     * id : 7672
     * userName : 7672
     * goodsId : 7672
     * goodsName : 趣味煮蛋模具
     * goodsEnglishName : Kotobuki
     * goodsThumb : http://121.197.1.20/images/201507/thumb_img/6372_thumb_G_1437108490316.jpg
     * goodsImg : http://121.197.1.20/images/201507/1437108490034171398.jpg
     * addTime : 1442419200000
     */

    private int id;
    private String userName;
    private int goodsId;
    private String goodsName;
    private String goodsEnglishName;
    private String goodsThumb;
    private String goodsImg;
    private long addTime;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsEnglishName(String goodsEnglishName) {
        this.goodsEnglishName = goodsEnglishName;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
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

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsEnglishName() {
        return goodsEnglishName;
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public long getAddTime() {
        return addTime;
    }

    public CollectBean() {
    }

    public CollectBean(String userName, int goodsId, String goodsName, String goodsEnglishName, String goodsThumb, String goodsImg, long addTime) {
        this.userName = userName;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsEnglishName = goodsEnglishName;
        this.goodsThumb = goodsThumb;
        this.goodsImg = goodsImg;
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsEnglishName='" + goodsEnglishName + '\'' +
                ", goodsThumb='" + goodsThumb + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", addTime=" + addTime +
                '}';
    }
}
