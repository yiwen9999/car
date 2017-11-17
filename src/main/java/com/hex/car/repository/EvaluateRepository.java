package com.hex.car.repository;

import com.hex.car.domain.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:21
 */
public interface EvaluateRepository extends JpaRepository<Evaluate, String>, JpaSpecificationExecutor {
    List<Evaluate> findTop4ByStateOrderByCreateTimeDesc(Integer state);
}
