package com.hex.car.repository;

import com.hex.car.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:27
 */
public interface ModelRepository extends JpaRepository<Model,String> {
}
