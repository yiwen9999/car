package com.hex.car.service;

import com.hex.car.domain.Place;
import com.hex.car.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * User: hexuan
 * Date: 2017/9/27
 * Time: 下午4:22
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Page<Place> findAllPlaceListByPage(Integer page, Integer size, Sort sort) {
        return placeRepository.findAll(new PageRequest(page, size, sort));
    }

    @Override
    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public void deletePlace(Place place) {
        placeRepository.delete(place);
    }

    @Override
    public Place findPlaceById(String id) {
        return placeRepository.findOne(id);
    }
}
