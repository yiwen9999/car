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
 * 4s店
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午10:31
 */
@Entity
public class Shop implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 4s店名称
     */
    @Column(length = 50)
    private String name;

    /**
     * 负责人
     */
    @Column(length = 50)
    private String linkman;

    /**
     * 详细地址
     */
    @Column(length = 100)
    private String address;

    /**
     * 客服电话
     */
    @Column(length = 50)
    private String customService;

    /**
     * 联系电话
     */
    @Column(length = 50)
    private String phone;

    /**
     * 邮箱
     */
    @Column(length = 100)
    private String email;

    /**
     * 简介
     */
    @Column(columnDefinition = "TEXT")
    private String remark;

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 所在区域
     */
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    /**
     * 登录账号
     */
    @OneToOne(optional = false, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    /**
     * 对应所有商品集合
     */
    @OneToMany(mappedBy = "shop")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    /**
     * 对应启用商品集合
     */
    @OneToMany(mappedBy = "shop")
    @Where(clause = "state = 2")
    @JsonIgnore
    private List<Product> usingProducts = new ArrayList<>();

    /**
     * 对应4s店图片
     */
    @OneToMany(targetEntity = ImgShop.class, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private List<ImgShop> imgShops = new ArrayList();

    /**
     * 4s店主图
     */
    @OneToMany(targetEntity = ImgShop.class)
    @Where(clause = "is_main=1")
    @JoinColumn(name = "shop_id")
    private List<ImgShop> mainImgShops = new ArrayList();

    /**
     * 4s店其他图
     */
    @OneToMany(targetEntity = ImgShop.class)
    @Where(clause = "is_main=0")
    @JoinColumn(name = "shop_id")
    private List<ImgShop> commonImgShops = new ArrayList();

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Shop() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getUsingProducts() {
        return usingProducts;
    }

    public void setUsingProducts(List<Product> usingProducts) {
        this.usingProducts = usingProducts;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getCustomService() {
        return customService;
    }

    public void setCustomService(String customService) {
        this.customService = customService;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ImgShop> getImgShops() {
        return imgShops;
    }

    public void setImgShops(List<ImgShop> imgShops) {
        this.imgShops = imgShops;
    }

    public List<ImgShop> getMainImgShops() {
        return mainImgShops;
    }

    public void setMainImgShops(List<ImgShop> mainImgShops) {
        this.mainImgShops = mainImgShops;
    }

    public List<ImgShop> getCommonImgShops() {
        return commonImgShops;
    }

    public void setCommonImgShops(List<ImgShop> commonImgShops) {
        this.commonImgShops = commonImgShops;
    }

}
