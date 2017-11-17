package com.hex.car.service;

import com.hex.car.domain.Shop;
import com.hex.car.repository.MySpec;
import com.hex.car.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<Shop> findTop4ByStateOrderByCreateTimeDesc(Integer state) {
        return shopRepository.findTop4ByStateOrderByCreateTimeDesc(state);
    }

    @Override
    public List<Shop> findTop10ShopsByNameLikeAndStateOrderByName(String name, Integer state) {
        return shopRepository.findTop10ShopsByNameLikeAndStateOrderByName("%" + name + "%", state);
    }

    @Override
    public Page<Shop> findShops(Map<String, Object> condition, PageRequest pageRequest) {
        return shopRepository.findAll(MySpec.findShops(condition), pageRequest);
    }

    @Override
    public List<Shop> findShopsByStateOrderByName(Integer state) {
        return shopRepository.findShopsByStateOrderByName(state);
    }
}
