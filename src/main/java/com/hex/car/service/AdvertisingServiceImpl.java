package com.hex.car.service;

import com.hex.car.domain.Advertising;
import com.hex.car.repository.AdvertisingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/29
 * Time: 下午2:56
 */
@Service
public class AdvertisingServiceImpl implements AdvertisingService {

    @Autowired
    private AdvertisingRepository advertisingRepository;

    @Override
    public Advertising saveAdvertising(Advertising advertising) {
        return advertisingRepository.save(advertising);
    }

    @Override
    public void deleteAdvertising(Advertising advertising) {
        advertisingRepository.delete(advertising);
    }

    @Override
    public Advertising findAdvertisingById(String id) {
        return advertisingRepository.findOne(id);
    }

    @Override
    public List<Advertising> findAllAdvertisingList() {
        return advertisingRepository.findAll();
    }
}
