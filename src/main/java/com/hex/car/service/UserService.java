package com.hex.car.service;

import com.hex.car.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

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

    List<User> findAllUser(Sort sort);

    Page<User> findUsers(Map<String, Object> condition, PageRequest pageRequest);
}
