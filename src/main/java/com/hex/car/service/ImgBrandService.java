package com.hex.car.service;

import com.hex.car.domain.ImgBrand;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:22
 */
public interface ImgBrandService {
    ImgBrand saveImgBrand(ImgBrand imgBrand);

    void deleteImgBrand(ImgBrand imgBrand);

    ImgBrand findImgBrandById(String id);

    List<ImgBrand> findAllImgBrand();
}
