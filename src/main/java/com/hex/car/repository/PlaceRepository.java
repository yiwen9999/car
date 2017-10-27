package com.hex.car.repository;

import com.hex.car.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:28
 */
public interface PlaceRepository extends JpaRepository<Place, String> {
    List<Place> findPlacesByLevelOrderById(Integer level);
}
