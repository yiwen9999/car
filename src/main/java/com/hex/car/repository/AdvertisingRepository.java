package com.hex.car.repository;

import com.hex.car.domain.Advertising;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:16
 */
public interface AdvertisingRepository extends JpaRepository<Advertising, String> {
}
