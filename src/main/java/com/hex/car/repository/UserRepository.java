package com.hex.car.repository;

import com.hex.car.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:30
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor {
    User findByUsername(String username);
}
