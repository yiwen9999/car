package com.hex.car.service;

import com.hex.car.domain.Parameter;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:35
 */
public interface ParameterService {
    Parameter saveParameter(Parameter parameter);

    void deleteParameter(Parameter parameter);

    Parameter findParameterById(String id);
}
