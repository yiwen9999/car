package com.hex.car.service;

import com.hex.car.domain.ImgProduct;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:22
 */
public interface ImgProductService {
    ImgProduct saveImgProduct(ImgProduct imgProduct);

    void deleteImgProduct(ImgProduct imgProduct);

    ImgProduct findImgProductById(String id);

    List<ImgProduct> findAllImgProduct();
}
