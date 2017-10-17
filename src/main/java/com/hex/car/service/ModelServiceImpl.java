package com.hex.car.service;

import com.hex.car.domain.Model;
import com.hex.car.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/23
 * Time: 下午2:09
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public Model saveModel(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public void deleteModel(Model model) {
        modelRepository.delete(model);
    }

    @Override
    public Model findModelById(String id) {
        return modelRepository.findOne(id);
    }

    @Override
    public List<Model> findAllModelList() {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        return modelRepository.findAll(sort);
    }
}
