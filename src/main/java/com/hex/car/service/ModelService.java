package com.hex.car.service;

import com.hex.car.domain.Model;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/23
 * Time: 下午2:08
 */
public interface ModelService {
    Model saveModel(Model model);

    void deleteModel(Model model);

    Model findModelById(String id);

    List<Model> findAllModelList();

    Model findFirstByName(String name);
}
