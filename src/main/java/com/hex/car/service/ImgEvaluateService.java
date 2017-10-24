package com.hex.car.service;

import com.hex.car.domain.ImgEvaluate;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:22
 */
public interface ImgEvaluateService {
    ImgEvaluate saveImgEvaluate(ImgEvaluate imgEvaluate);

    void deleteImgEvaluate(ImgEvaluate imgEvaluate);

    ImgEvaluate findImgEvaluateById(String id);

    List<ImgEvaluate> findAllImgEvaluate();
}
