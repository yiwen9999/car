package com.hex.car.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午1:25
 */
@Entity
public class BrowsingHistoryEvaluate implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 对应评测文章
     */
    @ManyToOne
    @JoinColumn(name = "evaluate_id")
    private Evaluate evaluate;

    /**
     * 对应用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public BrowsingHistoryEvaluate() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Evaluate getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
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
        return "BrowsingHistoryEvaluate{" +
                "id='" + id + '\'' +
                ", evaluate=" + evaluate +
                ", user=" + user +
                '}';
    }
}
