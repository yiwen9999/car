package com.hex.car.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 车型
 * User: hexuan
 * Date: 2017/9/18
 * Time: 上午11:31
 */
@Entity
public class CarType implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 车型名称
     */
    @Column(length = 50)
    private String name;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 车型对应图片
     */
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "img_car_type_id")
    private ImgCarType imgCarType;

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public CarType() {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ImgCarType getImgCarType() {
        return imgCarType;
    }

    public void setImgCarType(ImgCarType imgCarType) {
        this.imgCarType = imgCarType;
    }

    @Override
    public String toString() {
        return "CarType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", state=" + state +
                '}';
    }
}
