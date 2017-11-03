package com.hex.car.service;

import com.hex.car.domain.CarType;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:31
 */
public interface CarTypeService {
    CarType saveCarType(CarType carType);

    void deleteCarType(CarType carType);

    CarType findCarTypeById(String id);

    List<CarType> findCarTypesByStateOrderBySort(Integer state);
}
