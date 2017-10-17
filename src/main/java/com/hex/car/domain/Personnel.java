package com.hex.car.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午1:14
 */
@Entity
public class Personnel implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 昵称
     */
    @Column(length = 50)
    private String nickname;

    /**
     * 姓名
     */
    @Column(length = 50)
    private String name;

    /**
     * 手机号
     */
    @Column(length = 50)
    private String mobile;

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 登录账号
     */
    @OneToOne(optional = false, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    /**
     * 车辆收藏记录
     */
    @OneToMany(targetEntity = FavoritesProduct.class, mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FavoritesProduct> favoritesProducts = new ArrayList<>();

    /**
     * 评测文章收藏记录
     */
    @OneToMany(targetEntity = FavoritesEvaluate.class, mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FavoritesEvaluate> favoritesEvaluates = new ArrayList<>();

    /**
     * 车辆浏览记录
     */
    @OneToMany(targetEntity = BrowsingHistoryProduct.class, mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<BrowsingHistoryProduct> browsingHistoryCars = new ArrayList<>();

    /**
     * 文章浏览记录
     */
    @OneToMany(targetEntity = BrowsingHistoryEvaluate.class, mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<BrowsingHistoryEvaluate> browsingHistoryEvaluates = new ArrayList<>();

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Personnel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<FavoritesProduct> getFavoritesProducts() {
        return favoritesProducts;
    }

    public void setFavoritesProducts(List<FavoritesProduct> favoritesProducts) {
        this.favoritesProducts = favoritesProducts;
    }

    public List<FavoritesEvaluate> getFavoritesEvaluates() {
        return favoritesEvaluates;
    }

    public void setFavoritesEvaluates(List<FavoritesEvaluate> favoritesEvaluates) {
        this.favoritesEvaluates = favoritesEvaluates;
    }

    public List<BrowsingHistoryProduct> getBrowsingHistoryCars() {
        return browsingHistoryCars;
    }

    public void setBrowsingHistoryCars(List<BrowsingHistoryProduct> browsingHistoryCars) {
        this.browsingHistoryCars = browsingHistoryCars;
    }

    public List<BrowsingHistoryEvaluate> getBrowsingHistoryEvaluates() {
        return browsingHistoryEvaluates;
    }

    public void setBrowsingHistoryEvaluates(List<BrowsingHistoryEvaluate> browsingHistoryEvaluates) {
        this.browsingHistoryEvaluates = browsingHistoryEvaluates;
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
        return "Personnel{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", state=" + state +
                ", user=" + user +
                ", createTime=" + createTime +
                '}';
    }
}
