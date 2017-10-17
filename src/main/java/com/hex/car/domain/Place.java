package com.hex.car.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 地区
 * User: hexuan
 * Date: 2017/9/18
 * Time: 上午11:31
 */
@Entity
public class Place implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 名称
     */
    @Column(length = 50)
    private String name;

    /**
     * 拼音
     */
    @Column(length = 100)
    private String pinyin;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 父地点
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Place parent;

    /**
     * 子地点集合
     */
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy(value = "id")
    @JsonIgnore
    private List<Place> childs;

    /**
     * 在用子地点集合
     */
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy(value = "id")
    @Where(clause = "state=2")
    @JsonIgnore
    private List<Place> usingChilds;

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Place() {
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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Place getParent() {
        return parent;
    }

    public void setParent(Place parent) {
        this.parent = parent;
    }

    public List<Place> getChilds() {
        return childs;
    }

    public void setChilds(List<Place> childs) {
        this.childs = childs;
    }

    public List<Place> getUsingChilds() {
        return usingChilds;
    }

    public void setUsingChilds(List<Place> usingChilds) {
        this.usingChilds = usingChilds;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", level=" + level +
                ", parent=" + parent +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
