package com.hex.car.repository;

import com.hex.car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:16
 */
public interface CarRepository extends JpaRepository<Car, String> {
    /**
     * 根据名称，状态查询车辆集合，按名称排序
     *
     * @param name  名称
     * @param state 状态
     * @return
     */
    List<Car> findCarsByNameLikeAndStateOrderByName(String name, Integer state);

    List<Car> findTop10CarsByNameLikeAndStateOrderByName(String name, Integer state);

}
