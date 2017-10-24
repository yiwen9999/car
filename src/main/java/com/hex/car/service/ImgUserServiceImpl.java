package com.hex.car.service;

import com.hex.car.domain.ImgUser;
import com.hex.car.repository.ImgUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:33
 */
@Service
public class ImgUserServiceImpl implements ImgUserService {

    @Autowired
    private ImgUserRepository imgUserRepository;

    @Override
    public ImgUser saveImgUser(ImgUser imgUser) {
        return imgUserRepository.save(imgUser);
    }

    @Override
    public void deleteImgUser(ImgUser imgUser) {
        imgUserRepository.delete(imgUser);
    }

    @Override
    public ImgUser findImgUserById(String id) {
        return imgUserRepository.findOne(id);
    }

    @Override
    public List<ImgUser> findAllImgUser() {
        return imgUserRepository.findAll();
    }
}
