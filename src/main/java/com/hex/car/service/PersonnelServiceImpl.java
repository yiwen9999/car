package com.hex.car.service;

import com.hex.car.domain.Personnel;
import com.hex.car.repository.MySpec;
import com.hex.car.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public Personnel findFirstPersonnelByMobile(String mobile) {
        return personnelRepository.findFirstByMobile(mobile);
    }

    @Override
    public Page<Personnel> findPersonnels(Map<String, Object> condition, PageRequest pageRequest) {
        return personnelRepository.findAll(MySpec.findPersonnels(condition), pageRequest);
    }

    @Override
    public List<Personnel> findAllOrderByCreateTimeDesc() {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return personnelRepository.findAll(sort);
    }
}
