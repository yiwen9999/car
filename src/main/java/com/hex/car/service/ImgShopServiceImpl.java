package com.hex.car.service;

import com.hex.car.domain.ImgShop;
import com.hex.car.repository.ImgShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:33
 */
@Service
public class ImgShopServiceImpl implements ImgShopService {

    @Autowired
    private ImgShopRepository imgShopRepository;

    @Override
    public ImgShop saveImgShop(ImgShop imgShop) {
        return imgShopRepository.save(imgShop);
    }

    @Override
    public void deleteImgShop(ImgShop imgShop) {
        imgShopRepository.delete(imgShop);
    }

    @Override
    public ImgShop findImgShopById(String id) {
        return imgShopRepository.findOne(id);
    }

    @Override
    public List<ImgShop> findAllImgShop() {
        return imgShopRepository.findAll();
    }
}
