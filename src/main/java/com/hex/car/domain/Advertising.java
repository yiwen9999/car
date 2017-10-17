package com.hex.car.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 广告
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午12:00
 */
@Entity
public class Advertising implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 名称说明
     */
    private String name;

    /**
     * 跳转路径
     */
    @Column(length = 100)
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 广告图片
     */
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "img_ad_id")
    private ImgAD imgAD;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Advertising() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public ImgAD getImgAD() {
        return imgAD;
    }

    public void setImgAD(ImgAD imgAD) {
        this.imgAD = imgAD;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Advertising{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", sort=" + sort +
                ", state=" + state +
                ", imgAD=" + imgAD +
                '}';
    }
}
