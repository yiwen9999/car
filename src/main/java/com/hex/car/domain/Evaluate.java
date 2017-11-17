package com.hex.car.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 文章头图
     */
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "img_evaluate_id")
    private ImgEvaluate imgEvaluate;

    /**
     * 商品集合
     */
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "product_evaluate", joinColumns = @JoinColumn(name = "evaluate_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @OrderBy(value = "name")
    private List<Product> products = new ArrayList<>();

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Evaluate{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
