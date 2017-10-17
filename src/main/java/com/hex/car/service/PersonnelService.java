package com.hex.car.service;

import com.hex.car.domain.Personnel;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:38
 */
public interface PersonnelService {
    Personnel savePersonnel(Personnel personnel);

    void deletePersonnel(Personnel personnel);

    Personnel findPersonnelById(String id);
}
