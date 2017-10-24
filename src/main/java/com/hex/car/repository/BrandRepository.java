package com.hex.car.repository;

import com.hex.car.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:17
 */
public interface BrandRepository extends JpaRepository<Brand, String> {
    List<Brand> findBrandsByStateOrderByInitialAscNameAsc(Integer state);
}
