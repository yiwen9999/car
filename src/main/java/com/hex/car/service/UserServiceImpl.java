package com.hex.car.service;

import com.hex.car.domain.User;
import com.hex.car.repository.MySpec;
import com.hex.car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:45
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUser(Sort sort) {
        return userRepository.findAll(sort);
    }

    @Override
    public Page<User> findUsers(Map<String, Object> condition, PageRequest pageRequest) {
        return userRepository.findAll(MySpec.findUsers(condition), pageRequest);
    }
}
