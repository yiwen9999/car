package com.hex.car.service;

import com.hex.car.domain.Personnel;
import com.hex.car.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:39
 */
@Service
public class PersonnelServiceImpl implements PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Override
    public Personnel savePersonnel(Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    @Override
    public void deletePersonnel(Personnel personnel) {
        personnelRepository.delete(personnel);
    }

    @Override
    public Personnel findPersonnelById(String id) {
        return personnelRepository.findOne(id);
    }
}
