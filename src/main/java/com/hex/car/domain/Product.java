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
 * 产品
 * User: hexuan
 * Date: 2017/9/28
 * Time: 上午10:39
 */
@Entity
public class Product implements Serializable {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 车辆名称
     */
    @Column(length = 50)
    private String name;

    /**
     * 详情说明
     */
    private String details;

    /**
     * 售价
     */
    private Double price;

    /**
     * 对应4s店
     */
    @ManyToOne(targetEntity = Shop.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;

    /**
     * 对应车辆
     */
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    /**
     * 对应商品图片
     */
    @OneToMany(targetEntity = ImgProduct.class, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private List<ImgProduct> imgProducts = new ArrayList();

    /**
     * 商品主图
     */
    @OneToMany(targetEntity = ImgProduct.class)
    @Where(clause = "img_type=0")
    @JoinColumn(name = "product_id")
    private List<ImgProduct> mainImgProducts = new ArrayList();

    /**
     * banner图
     */
    @OneToMany(targetEntity = ImgProduct.class)
    @Where(clause = "img_type=1")
    @JoinColumn(name = "product_id")
    private List<ImgProduct> bannerImgProducts = new ArrayList();

    /**
     * 商品其他图
     */
    @OneToMany(targetEntity = ImgProduct.class)
    @Where(clause = "img_type=2")
    @JoinColumn(name = "product_id")
    private List<ImgProduct> commonImgProducts = new ArrayList();

    /**
     * 对应的评测文章集合
     */
    @ManyToMany(mappedBy = "products")
    @OrderBy(value = "create_time desc")
    @JsonIgnore
    private List<Evaluate> evaluates = new ArrayList<>();

    /**
     * 对应启用的评测文章集合
     */
    @ManyToMany(mappedBy = "products")
    @Where(clause = "state=2")
    @OrderBy(value = "create_time desc")
    @JsonIgnore
    private List<Evaluate> usingEvaluates = new ArrayList<>();

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Product() {
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<ImgProduct> getImgProducts() {
        return imgProducts;
    }

    public void setImgProducts(List<ImgProduct> imgProducts) {
        this.imgProducts = imgProducts;
    }

    public List<Evaluate> getUsingEvaluates() {
        return usingEvaluates;
    }

    public void setUsingEvaluates(List<Evaluate> usingEvaluates) {
        this.usingEvaluates = usingEvaluates;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<ImgProduct> getMainImgProducts() {
        return mainImgProducts;
    }

    public void setMainImgProducts(List<ImgProduct> mainImgProducts) {
        this.mainImgProducts = mainImgProducts;
    }

    public List<ImgProduct> getCommonImgProducts() {
        return commonImgProducts;
    }

    public void setCommonImgProducts(List<ImgProduct> commonImgProducts) {
        this.commonImgProducts = commonImgProducts;
    }

    public List<Evaluate> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<Evaluate> evaluates) {
        this.evaluates = evaluates;
    }

    public List<ImgProduct> getBannerImgProducts() {
        return bannerImgProducts;
    }

    public void setBannerImgProducts(List<ImgProduct> bannerImgProducts) {
        this.bannerImgProducts = bannerImgProducts;
    }
}
