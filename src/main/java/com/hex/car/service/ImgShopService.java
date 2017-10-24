package com.hex.car.service;

import com.hex.car.domain.ImgShop;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:22
 */
public interface ImgShopService {
    ImgShop saveImgShop(ImgShop imgShop);

    void deleteImgShop(ImgShop imgShop);

    ImgShop findImgShopById(String id);

    List<ImgShop> findAllImgShop();
}
