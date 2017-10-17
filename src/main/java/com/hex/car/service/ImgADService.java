package com.hex.car.service;

import com.hex.car.domain.ImgAD;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:31
 */
public interface ImgADService {
    ImgAD saveImgAD(ImgAD imgAD);

    void deleteImgAD(ImgAD imgAD);

    ImgAD findImgADById(String id);
}
