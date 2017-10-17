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
}
