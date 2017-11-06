package com.hex.car.service;

import com.hex.car.domain.Parameter;
import com.hex.car.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Parameter> findParametersByStateOrderBySort(Integer state) {
        return parameterRepository.findParametersByStateOrderBySort(state);
    }

    @Override
    public List<Parameter> findTopParameterListOrderBySort() {
        return parameterRepository.findParametersByParentIsNullOrderBySort();
    }

    @Override
    public List<Parameter> findChildParameterListOrderBySort() {
        return parameterRepository.findParametersByParentIsNotNullOrderBySort();
    }

    @Override
    public List<Parameter> findUsingTopParameterListOrderBySort(Integer state) {
        return parameterRepository.findParametersByParentIsNotNullAndStateOrderBySort(state);
    }

    @Override
    public List<Parameter> findUsingChildParameterListOrderBySort(Integer state) {
        return parameterRepository.findParametersByParentIsNotNullAndStateOrderBySort(state);
    }

    @Override
    public Parameter findFirstByCode(String code) {
        return parameterRepository.findFirstByCode(code);
    }
}
