package com.hex.car.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论
 * User: hexuan
 * Date: 2017/9/18
 * Time: 上午11:48
 */
@Entity
public class Review implements Serializable{

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 评论内容
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
     * 对应评测文章
     */
    @ManyToOne
    @JoinColumn(name = "evaluate_id")
    private Evaluate evaluate;

    /**
     * 对应用户
     */
    private User user;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Review() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "Review{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", evaluate=" + evaluate +
                ", user=" + user +
                '}';
    }
}
