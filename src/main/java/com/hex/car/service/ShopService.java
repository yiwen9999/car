package com.hex.car.service;

import com.hex.car.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

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

    List<Shop> findTop10ShopsByNameLikeAndStateOrderByName(String name, Integer state);

    Page<Shop> findShops(Map<String, Object> condition, PageRequest pageRequest);

    List<Shop> findShopsByStateOrderByName(Integer state);
}
