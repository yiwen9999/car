package com.hex.car.service;

import com.hex.car.domain.Shop;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:22
 */
public interface ShopService {
    Shop saveShop(Shop shop);

    void deleteShop(Shop shop);

    Shop findShopById(String id);

    List<Shop> findAllShop();

    List<Shop> findTop4ByStateOrderByCreateTimeDesc(Integer state);
}
