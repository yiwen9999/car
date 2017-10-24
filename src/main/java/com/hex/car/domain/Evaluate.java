package com.hex.car.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 评测
 * User: hexuan
 * Date: 2017/9/18
 * Time: 上午11:49
 */
@Entity
public class Evaluate implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 标题名称
     */
    @Column(length = 50)
    private String title;

    /**
     * 评测内容
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String content;

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 对应商品
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * 文章头图
     */
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "img_evaluate_id")
    private ImgEvaluate imgEvaluate;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Evaluate() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ImgEvaluate getImgEvaluate() {
        return imgEvaluate;
    }

    public void setImgEvaluate(ImgEvaluate imgEvaluate) {
        this.imgEvaluate = imgEvaluate;
    }

    @Override
    public String toString() {
        return "Evaluate{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", product=" + product +
                ", createTime=" + createTime +
                '}';
    }
}
