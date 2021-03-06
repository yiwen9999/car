package com.hex.car.service;

import com.hex.car.domain.CarType;
import com.hex.car.repository.CarTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:35
 */
@Service
public class CarTypeServiceImpl implements CarTypeService {

    @Autowired
    private CarTypeRepository carTypeRepository;

    @Override
    public CarType saveCarType(CarType carType) {
        return carTypeRepository.save(carType);
    }

    @Override
    public void deleteCarType(CarType carType) {
        carTypeRepository.delete(carType);
    }

    @Override
    public CarType findCarTypeById(String id) {
        return carTypeRepository.findOne(id);
    }

    @Override
    public List<CarType> findAllCarTypeList() {
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        return carTypeRepository.findAll(sort);
    }

    @Override
    public List<CarType> findCarTypesByStateOrderBySort(Integer state) {
        return carTypeRepository.findCarTypesByStateOrderBySort(state);
    }
}
