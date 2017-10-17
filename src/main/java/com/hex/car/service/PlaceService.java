package com.hex.car.service;

import com.hex.car.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * User: hexuan
 * Date: 2017/9/27
 * Time: 下午4:22
 */
public interface PlaceService {
    Page<Place> findAllPlaceListByPage(Integer page, Integer size, Sort sort);

    Place savePlace(Place place);

    void deletePlace(Place place);

    Place findPlaceById(String id);
}
