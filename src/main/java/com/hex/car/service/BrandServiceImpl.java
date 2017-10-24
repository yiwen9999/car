package com.hex.car.service;

import com.hex.car.domain.Brand;
import com.hex.car.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/23
 * Time: 下午2:33
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Brand brand) {
        brandRepository.delete(brand);
    }

    @Override
    public Brand findBrandById(String id) {
        return brandRepository.findOne(id);
    }

    @Override
    public List<Brand> findAllBrandList() {
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "initial");
        Sort.Order order2 = new Sort.Order(Sort.Direction.ASC, "name");
        orders.add(order1);
        orders.add(order2);
        Sort sort = new Sort(orders);
        return brandRepository.findAll(sort);
    }

    @Override
    public List<Brand> findBrandListByState(Integer state) {
        return brandRepository.findBrandsByStateOrderByInitialAscNameAsc(state);
    }
}
