package com.hex.car.service;

import com.hex.car.domain.Personnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:38
 */
public interface PersonnelService {
    Personnel savePersonnel(Personnel personnel);

    void deletePersonnel(Personnel personnel);

    Personnel findPersonnelById(String id);

    Personnel findFirstPersonnelByMobile(String mobile);

    Page<Personnel> findPersonnels(Map<String, Object> condition, PageRequest pageRequest);

    List<Personnel> findAllOrderByCreateTimeDesc();
}
