package com.hex.car.repository;

import com.hex.car.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: hexuan
 * Date: 2017/9/18
 * Time: 下午4:30
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
