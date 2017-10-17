package com.hex.car.repository;

import com.hex.car.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:29
 */
public interface ReviewRepository extends JpaRepository<Review,String> {
}
