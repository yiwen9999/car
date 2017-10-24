package com.hex.car.repository;

import com.hex.car.domain.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hexuan
 * Date: 2017/9/22
 * Time: 上午9:39
 */
public interface PersonnelRepository extends JpaRepository<Personnel, String> {
}
