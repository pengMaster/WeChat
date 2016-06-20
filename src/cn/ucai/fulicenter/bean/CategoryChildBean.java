package cn.ucai.fulicenter.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class CategoryChildBean implements Serializable {

    /**
     * id : 348
     * parentId : 344
     * name : 败姐推荐
     * imageUrl : cat_image/256_4.png
     */

    private int id;
    private int parentId;
    private String name;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CategoryChildBean{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
