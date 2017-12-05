package com.hex.car.service;

import com.hex.car.domain.Car;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:22
 */
public interface CarService {
    Car saveCar(Car car);

    void deleteCar(Car car);

    Car findCarById(String id);

    List<Car> findAllCar();

    /**
     * 根据名称，状态查询车辆集合，按名称排序
     *
     * @param name  名称
     * @param state 状态
     * @return
     */
    List<Car> findCarsByNameLikeAndStateOrderByName(String name, Integer state);

    List<Car> saveCarList(List<Car> cars);

    /**
     * 根据名称模糊，状态，查询10个车辆集合，按名称排序
     *
     * @param name
     * @param state
     * @return
     */
    List<Car> findTop10CarsByNameLikeAndStateOrderByNameDesc(String name, Integer state);
}
