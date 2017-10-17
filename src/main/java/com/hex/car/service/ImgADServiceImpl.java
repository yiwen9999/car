package com.hex.car.service;

import com.hex.car.domain.ImgAD;
import com.hex.car.repository.ImgADRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:32
 */
@Service
public class ImgADServiceImpl implements ImgADService {

    @Autowired
    private ImgADRepository imgADRepository;

    @Override
    public ImgAD saveImgAD(ImgAD imgAD) {
        return imgADRepository.save(imgAD);
    }

    @Override
    public void deleteImgAD(ImgAD imgAD) {
        imgADRepository.delete(imgAD);
    }

    @Override
    public ImgAD findImgADById(String id) {
        return imgADRepository.findOne(id);
    }
}
