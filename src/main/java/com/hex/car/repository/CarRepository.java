package com.hex.car.repository;

import com.hex.car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:16
 */
public interface CarRepository extends JpaRepository<Car, String> {
}
