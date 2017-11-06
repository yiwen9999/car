package com.hex.car.service;

import com.hex.car.domain.Parameter;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:35
 */
public interface ParameterService {
    Parameter saveParameter(Parameter parameter);

    void deleteParameter(Parameter parameter);

    Parameter findParameterById(String id);

    /**
     * 根据状态查询参数集合，按排序号排序
     *
     * @param state
     * @return
     */
    List<Parameter> findParametersByStateOrderBySort(Integer state);

    List<Parameter> findTopParameterListOrderBySort();

    List<Parameter> findChildParameterListOrderBySort();

    List<Parameter> findUsingTopParameterListOrderBySort(Integer state);

    List<Parameter> findUsingChildParameterListOrderBySort(Integer state);

    Parameter findFirstByCode(String code);
}
