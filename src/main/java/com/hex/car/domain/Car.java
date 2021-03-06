package com.hex.car.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 车辆
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午10:35
 */
@Entity
public class Car implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    @Column(length = 50)
    private String id;

    /**
     * 名称，用于检索
     */
    @Column(length = 100)
    private String name;

    /**
     * 排量
     */
    private Double displacement;

    /**
     * 年度
     */
    private Integer year;

    /**
     * 品牌
     */
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    /**
     * 型号
     */
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    /**
     * 车型
     */
    @ManyToOne
    @JoinColumn(name = "car_type_id")
    private CarType carType;

    /**
     * 进气形式
     */
    @ManyToOne
    @JoinColumn(name = "engine_type_id")
    private Parameter engineType;

    /**
     * 驱动方式
     */
    @ManyToOne
    @JoinColumn(name = "drivetrain_id")
    private Parameter drivetrain;

    /**
     * 变速箱
     */
    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Parameter transmission;

    /**
     * 能源
     */
    @ManyToOne
    @JoinColumn(name = "fuel_type_id")
    private Parameter fuelType;

    /**
     * 结构
     */
    @ManyToOne
    @JoinColumn(name = "body_type_id")
    private Parameter bodyType;

    /**
     * 座位数
     */
    @ManyToOne
    @JoinColumn(name = "seats_id")
    private Parameter seats;

    /**
     * 状态
     */
    private Integer state = new Integer(2);

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    public Car() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Double displacement) {
        this.displacement = displacement;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public Parameter getEngineType() {
        return engineType;
    }

    public void setEngineType(Parameter engineType) {
        this.engineType = engineType;
    }

    public Parameter getDrivetrain() {
        return drivetrain;
    }

    public void setDrivetrain(Parameter drivetrain) {
        this.drivetrain = drivetrain;
    }

    public Parameter getTransmission() {
        return transmission;
    }

    public void setTransmission(Parameter transmission) {
        this.transmission = transmission;
    }

    public Parameter getFuelType() {
        return fuelType;
    }

    public void setFuelType(Parameter fuelType) {
        this.fuelType = fuelType;
    }

    public Parameter getBodyType() {
        return bodyType;
    }

    public void setBodyType(Parameter bodyType) {
        this.bodyType = bodyType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parameter getSeats() {
        return seats;
    }

    public void setSeats(Parameter seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", displacement=" + displacement +
                ", year=" + year +
                ", brand=" + brand +
                ", model=" + model +
                ", carType=" + carType +
                ", engineType=" + engineType +
                ", drivetrain=" + drivetrain +
                ", transmission=" + transmission +
                ", fuelType=" + fuelType +
                ", bodyType=" + bodyType +
                ", seats=" + seats +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
