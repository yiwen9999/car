package com.hex.car.service;

import com.hex.car.domain.ImgBrand;
import com.hex.car.repository.ImgBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:33
 */
@Service
public class ImgBrandServiceImpl implements ImgBrandService {

    @Autowired
    private ImgBrandRepository imgBrandRepository;

    @Override
    public ImgBrand saveImgBrand(ImgBrand imgBrand) {
        return imgBrandRepository.save(imgBrand);
    }

    @Override
    public void deleteImgBrand(ImgBrand imgBrand) {
        imgBrandRepository.delete(imgBrand);
    }

    @Override
    public ImgBrand findImgBrandById(String id) {
        return imgBrandRepository.findOne(id);
    }

    @Override
    public List<ImgBrand> findAllImgBrand() {
        return imgBrandRepository.findAll();
    }
}
