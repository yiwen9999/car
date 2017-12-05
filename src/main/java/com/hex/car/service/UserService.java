package com.hex.car.service;

import com.hex.car.domain.User;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:45
 */
public interface UserService {
    User saveUser(User user);

    void deleteUser(User user);

    User findUserById(String id);

    User findUserByUsername(String username);

    List<User> findAllUser();
}
