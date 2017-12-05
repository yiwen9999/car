package com.hex.car.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 收藏车辆
 * User: hexuan
 * Date: 2017/9/18
 * Time: 上午11:37
 */
@Entity
public class FavoritesProduct implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 对应商品
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * 对应用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public FavoritesProduct() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FavoritesProduct{" +
                "id='" + id + '\'' +
                ", product=" + product +
                ", user=" + user +
                ", createTime=" + createTime +
                '}';
    }
}
