package com.hex.car.repository;

import com.hex.car.domain.ImgUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:19
 */
public interface ImgUserRepository extends JpaRepository<ImgUser, String> {
}
