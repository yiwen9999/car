package com.hex.car.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 车辆图片
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午10:41
 */
@Entity
public class ImgProduct implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 图片文件名称
     */
    @Column(length = 50)
    private String fileName;

    /**
     * 图片类型 （0为主图，1为banner图，2为详情图）
     */
    private Integer imgType = new Integer(2);

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public ImgProduct() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getImgType() {
        return imgType;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }

    @Override
    public String toString() {
        return "ImgProduct{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
