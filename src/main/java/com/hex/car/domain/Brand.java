package com.hex.car.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 品牌
 * User: hexuan
 * Date: 2017/9/18
 * Time: 上午11:31
 */
@Entity
public class Brand implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 品牌名称
     */
    @Column(length = 50)
    private String name;

    /**
     * 首字母
     */
    @Column(length = 5)
    private String initial;

    /**
     * 品牌对应图片
     */
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "img_brand_id")
    private ImgBrand imgBrand;

    /**
     * 型号集合
     */
    @OneToMany(mappedBy = "brand", cascade = {CascadeType.PERSIST})
    @JsonIgnore
    private List<Model> models = new ArrayList<>();

    /**
     * 在用型号集合
     */
    @OneToMany(mappedBy = "brand")
    @Where(clause = "state=2")
    @JsonIgnore
    private List<Model> usingModels = new ArrayList<>();

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Brand() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public List<Model> getUsingModels() {
        return usingModels;
    }

    public void setUsingModels(List<Model> usingModels) {
        this.usingModels = usingModels;
    }

    public ImgBrand getImgBrand() {
        return imgBrand;
    }

    public void setImgBrand(ImgBrand imgBrand) {
        this.imgBrand = imgBrand;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", initial='" + initial + '\'' +
                ", state=" + state +
                '}';
    }
}
