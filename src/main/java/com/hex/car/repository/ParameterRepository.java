package com.hex.car.repository;

import com.hex.car.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:17
 */
public interface ParameterRepository extends JpaRepository<Parameter,String> {
}
