package com.hex.car.service;

import com.hex.car.domain.Shop;
import com.hex.car.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:41
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public Shop saveShop(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public void deleteShop(Shop shop) {
        shopRepository.delete(shop);
    }

    @Override
    public Shop findShopById(String id) {
        return shopRepository.findShopById(id);
    }

    @Override
    public List<Shop> findAllShop() {
        return shopRepository.findAll();
    }
}
