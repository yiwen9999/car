package com.hex.car.service;

import com.hex.car.domain.ImgUser;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:22
 */
public interface ImgUserService {
    ImgUser saveImgUser(ImgUser imgUser);

    void deleteImgUser(ImgUser imgUser);

    ImgUser findImgUserById(String id);

    List<ImgUser> findAllImgUser();
}
