package com.hex.car.service;

import com.hex.car.domain.ImgProduct;
import com.hex.car.repository.ImgProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:33
 */
@Service
public class ImgProductServiceImpl implements ImgProductService {

    @Autowired
    private ImgProductRepository imgProductRepository;

    @Override
    public ImgProduct saveImgProduct(ImgProduct imgProduct) {
        return imgProductRepository.save(imgProduct);
    }

    @Override
    public void deleteImgProduct(ImgProduct imgProduct) {
        imgProductRepository.delete(imgProduct);
    }

    @Override
    public ImgProduct findImgProductById(String id) {
        return imgProductRepository.findOne(id);
    }

    @Override
    public List<ImgProduct> findAllImgProduct() {
        return imgProductRepository.findAll();
    }
}
