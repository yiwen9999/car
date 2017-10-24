package com.hex.car.service;

import com.hex.car.domain.ImgEvaluate;
import com.hex.car.repository.ImgEvaluateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:33
 */
@Service
public class ImgEvaluateServiceImpl implements ImgEvaluateService {

    @Autowired
    private ImgEvaluateRepository imgEvaluateRepository;

    @Override
    public ImgEvaluate saveImgEvaluate(ImgEvaluate imgEvaluate) {
        return imgEvaluateRepository.save(imgEvaluate);
    }

    @Override
    public void deleteImgEvaluate(ImgEvaluate imgEvaluate) {
        imgEvaluateRepository.delete(imgEvaluate);
    }

    @Override
    public ImgEvaluate findImgEvaluateById(String id) {
        return imgEvaluateRepository.findOne(id);
    }

    @Override
    public List<ImgEvaluate> findAllImgEvaluate() {
        return imgEvaluateRepository.findAll();
    }
}
