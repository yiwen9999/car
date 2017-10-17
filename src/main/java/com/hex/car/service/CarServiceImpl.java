package com.hex.car.service;

import com.hex.car.domain.Car;
import com.hex.car.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:36
 */
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Car car) {
        carRepository.delete(car);
    }

    @Override
    public Car findCarById(String id) {
        return carRepository.findOne(id);
    }

    @Override
    public List<Car> findAllCar() {
        return carRepository.findAll();
    }
}
