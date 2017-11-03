package com.hex.car.repository;

import com.hex.car.domain.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:20
 */
public interface CarTypeRepository extends JpaRepository<CarType, String> {
    List<CarType> findCarTypesByStateOrderBySort(Integer state);
}
