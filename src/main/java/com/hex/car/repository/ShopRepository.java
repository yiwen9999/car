package com.hex.car.repository;

import com.hex.car.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:18
 */
public interface ShopRepository extends JpaRepository<Shop, String> {
    Shop findShopById(String id);

    List<Shop> findTop4ByStateOrderByCreateTimeDesc(Integer state);
}
