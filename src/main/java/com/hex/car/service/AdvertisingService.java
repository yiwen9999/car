package com.hex.car.service;

import com.hex.car.domain.Advertising;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/29
 * Time: 下午2:54
 */
public interface AdvertisingService {
    Advertising saveAdvertising(Advertising advertising);

    void deleteAdvertising(Advertising advertising);

    Advertising findAdvertisingById(String id);

    List<Advertising> findAllAdvertisingList();

    /**
     * 根据状态查询广告集合，按排序号排序
     *
     * @param state
     * @return
     */
    List<Advertising> findAdvertisingsByStateOrderBySort(Integer state);
}
