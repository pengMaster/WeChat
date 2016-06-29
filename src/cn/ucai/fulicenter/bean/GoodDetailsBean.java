package cn.ucai.fulicenter.bean;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by ucai001 on 2016/3/1.
 */
public class GoodDetailsBean implements Serializable {

    /**
     * id : 280
     * goodsId : 7677
     * catId : 291
     * goodsName : 双层分格饭盒 绿色
     * goodsEnglishName : Monbento
     * goodsBrief : PP食品级材质，轻巧、易清洗、蠕变性小，不易变形，可置于微波炉加热，可方巾洗碗机清洗。双层色彩可以随意组合，轻巧方便。
     * shopPrice : ￥253
     * currencyPrice : ￥293
     * promotePrice : ￥0
     * rankPrice : ￥293
     * goodsThumb : 201509/thumb_img/7677_thumb_G_1442391216339.png
     * goodsImg : 201509/thumb_img/7677_thumb_G_1442391216339.png
     * addTime : 1442419200000
     * shareUrl : http://m.fulishe.com/item/7677
     * properties : [{"id":9529,"goodsId":0,"colorId":7,"colorName":"白色","colorCode":"#ffffff","colorImg":"","colorUrl":"https://detail.tmall.com/item.htm?spm=a1z10.5-b.w4011-3609973698.66.6PtkVY&id=520971761592&rn=5ddf7aff64dbe1a24da0eaf7409e3389&abbucket=15&skuId=3104519239252","albums":[{"pid":7677,"imgId":28296,"imgUrl":"201509/goods_img/7677_P_1442391216432.png","thumbUrl":"no_picture.gif"}]}]
     * promote : false
     */

    private int id;
    private int goodsId;
    private int catId;
    private String goodsName;
    private String goodsEnglishName;
    private String goodsBrief;
    private String shopPrice;
    private String currencyPrice;
    private String promotePrice;
    private String rankPrice;
    private String goodsThumb;
    private String goodsImg;
    private long addTime;
    private String shareUrl;
    @JsonProperty("isPromote")
    private boolean isPromote;
    /**
     * id : 9529
     * goodsId : 0
     * colorId : 7
     * colorName : 白色
     * colorCode : #ffffff
     * colorImg :
     * colorUrl : https://detail.tmall.com/item.htm?spm=a1z10.5-b.w4011-3609973698.66.6PtkVY&id=520971761592&rn=5ddf7aff64dbe1a24da0eaf7409e3389&abbucket=15&skuId=3104519239252
     * albums : [{"pid":7677,"imgId":28296,"imgUrl":"201509/goods_img/7677_P_1442391216432.png","thumbUrl":"no_picture.gif"}]
     */

    private Properties[] properties;

    public void setId(int id) {
        this.id = id;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsEnglishName(String goodsEnglishName) {
        this.goodsEnglishName = goodsEnglishName;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public void setCurrencyPrice(String currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    public void setPromotePrice(String promotePrice) {
        this.promotePrice = promotePrice;
    }

    public void setRankPrice(String rankPrice) {
        this.rankPrice = rankPrice;
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

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public void setPromote(boolean isPromote) {
        this.isPromote = isPromote;
    }

    public void setProperties(Properties[] properties) {
        this.properties = properties;
    }

    public int getId() {
        return id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public int getCatId() {
        return catId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsEnglishName() {
        return goodsEnglishName;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public String getCurrencyPrice() {
        return currencyPrice;
    }

    public String getPromotePrice() {
        return promotePrice;
    }

    public String getRankPrice() {
        return rankPrice;
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

    public String getShareUrl() {
        return shareUrl;
    }

    @JsonIgnore
    public boolean isPromote() {
        return isPromote;
    }

    public Properties[] getPropertyBean() {
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodDetailsBean that = (GoodDetailsBean) o;

        return goodsId == that.goodsId;

    }

    @Override
    public int hashCode() {
        return goodsId;
    }

    @Override
    public String toString() {
        return "GoodDetailsBean{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", catId=" + catId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsEnglishName='" + goodsEnglishName + '\'' +
                ", goodsBrief='" + goodsBrief + '\'' +
                ", shopPrice='" + shopPrice + '\'' +
                ", currencyPrice='" + currencyPrice + '\'' +
                ", promotePrice='" + promotePrice + '\'' +
                ", rankPrice='" + rankPrice + '\'' +
                ", goodsThumb='" + goodsThumb + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", addTime=" + addTime +
                ", shareUrl='" + shareUrl + '\'' +
                ", isPromote=" + isPromote +
                ", properties=" + Arrays.toString(properties) +
                '}';
    }
}
