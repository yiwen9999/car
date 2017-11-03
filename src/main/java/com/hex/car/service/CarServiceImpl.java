package com.hex.car.service;

import com.hex.car.domain.Car;
import com.hex.car.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        return carRepository.findAll(sort);
    }

    @Override
    public List<Car> findCarsByNameLikeAndStateOrderByName(String name, Integer state) {
        return carRepository.findCarsByNameLikeAndStateOrderByName(name, state);
    }

    @Override
    @Transactional
    public List<Car> saveCarList(List<Car> cars) {
        return carRepository.save(cars);
    }
}
