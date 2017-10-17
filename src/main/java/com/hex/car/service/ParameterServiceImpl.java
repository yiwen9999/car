package com.hex.car.service;

import com.hex.car.domain.Parameter;
import com.hex.car.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:36
 */
@Service
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public Parameter saveParameter(Parameter parameter) {
        return parameterRepository.save(parameter);
    }

    @Override
    public void deleteParameter(Parameter parameter) {
        parameterRepository.delete(parameter);
    }

    @Override
    public Parameter findParameterById(String id) {
        return parameterRepository.findOne(id);
    }
}
