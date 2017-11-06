package com.hex.car.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * 参数
 * <p>
 * User: hexuan
 * Date: 2017/9/25
 * Time: 上午10:25
 */
@Entity
public class Parameter {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数code
     */
    private String code;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 父参数
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parameter parent;

    /**
     * 子参数集合
     */
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy(value = "sort")
    @JsonIgnore
    private List<Parameter> childs;

    /**
     * 在用子集合
     */
    @OneToMany(mappedBy = "parent")
    @OrderBy(value = "sort")
    @Where(clause = "state=2")
    @JsonIgnore
    private List<Parameter> usingChilds;

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    public Parameter() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Parameter getParent() {
        return parent;
    }

    public void setParent(Parameter parent) {
        this.parent = parent;
    }

    public List<Parameter> getChilds() {
        return childs;
    }

    public void setChilds(List<Parameter> childs) {
        this.childs = childs;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Parameter> getUsingChilds() {
        return usingChilds;
    }

    public void setUsingChilds(List<Parameter> usingChilds) {
        this.usingChilds = usingChilds;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sort=" + sort +
                ", parent=" + parent +
                ", state=" + state +
                '}';
    }
}
