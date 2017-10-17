package com.hex.car.service;

import com.hex.car.domain.Brand;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/23
 * Time: 下午2:12
 */
public interface BrandService {
    Brand saveBrand(Brand brand);

    void deleteBrand(Brand brand);

    Brand findBrandById(String id);

    List<Brand> findAllBrandList();
}
