package com.hex.car.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 登录账号
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午1:14
 */
@Entity
public class User implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 登录名
     */
    @Column(length = 50)
    private String username;

    /**
     * 密码
     */
    @Column(length = 100)
    private String password;

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 对应4s店
     */
    @OneToOne(targetEntity = Shop.class, mappedBy = "user", optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Shop shop;

    /**
     * 对应用户
     */
    @OneToOne(cascade = {CascadeType.ALL}, targetEntity = Personnel.class, mappedBy = "user", optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Personnel personnel;

    /**
     * 用户头像
     */
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "img_user_id")
    private ImgUser imgUser;

    /**
     * 车辆收藏记录
     */
    @OneToMany(targetEntity = FavoritesProduct.class, mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @OrderBy("create_time desc")
    //    @JsonIgnore
    private List<FavoritesProduct> favoritesProducts = new ArrayList<>();

    /**
     * 车辆浏览记录
     */
    @OneToMany(targetEntity = BrowsingHistoryProduct.class, mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @OrderBy("create_time desc")
    //    @JsonIgnore
    private List<BrowsingHistoryProduct> browsingHistoryCars = new ArrayList<>();

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public ImgUser getImgUser() {
        return imgUser;
    }

    public void setImgUser(ImgUser imgUser) {
        this.imgUser = imgUser;
    }

    public List<FavoritesProduct> getFavoritesProducts() {
        return favoritesProducts;
    }

    public void setFavoritesProducts(List<FavoritesProduct> favoritesProducts) {
        this.favoritesProducts = favoritesProducts;
    }

    public List<BrowsingHistoryProduct> getBrowsingHistoryCars() {
        return browsingHistoryCars;
    }

    public void setBrowsingHistoryCars(List<BrowsingHistoryProduct> browsingHistoryCars) {
        this.browsingHistoryCars = browsingHistoryCars;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", shop=" + shop +
                ", personnel=" + personnel +
                ", createTime=" + createTime +
                '}';
    }
}
