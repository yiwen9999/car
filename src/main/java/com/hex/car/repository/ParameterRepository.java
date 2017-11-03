package com.hex.car.repository;

import com.hex.car.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:17
 */
public interface ParameterRepository extends JpaRepository<Parameter, String> {
    List<Parameter> findParametersByStateOrderBySort(Integer state);

    List<Parameter> findParametersByParentIsNullOrderBySort();

    List<Parameter> findParametersByParentIsNotNullOrderBySort();

    List<Parameter> findParametersByParentIsNullAndStateOrderBySort(Integer state);

    List<Parameter> findParametersByParentIsNotNullAndStateOrderBySort(Integer state);
}
