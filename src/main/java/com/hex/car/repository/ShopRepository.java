package com.hex.car.repository;

import com.hex.car.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:18
 */
public interface ShopRepository extends JpaRepository<Shop, String>, JpaSpecificationExecutor {
    Shop findShopById(String id);

    List<Shop> findTop4ByStateOrderByCreateTimeDesc(Integer state);

    List<Shop> findTop10ShopsByNameLikeAndStateOrderByName(String name, Integer state);
}
