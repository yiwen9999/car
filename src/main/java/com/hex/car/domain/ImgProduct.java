package com.hex.car.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
     * 是否为主图
     */
    private Boolean isMain;

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

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }

    @Override
    public String toString() {
        return "ImgProduct{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", isMain=" + isMain +
                ", createTime=" + createTime +
                '}';
    }
}
