package com.hex.car.controller;

import com.hex.car.domain.User;
import com.hex.car.service.UserService;
import com.hex.car.utils.Md5SaltTool;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * User: hexuan
 * Date: 2017/10/17
 * Time: 上午11:35
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登入
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public Object login(String username, String password, HttpServletRequest request) {
        User user = userService.findUserByUsername(username);
        boolean result = false;
        try {
            /**
             * 先判断操作帐号是否启用
             * 再判断密码是否正确
             */
            if (2 == user.getState()) {
                result = Md5SaltTool.validPassword(password, user.getPassword());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResultUtil.error(1, e.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResultUtil.error(1, e.toString());
        }
        if (result) {
            request.getSession().setAttribute("user", user);
        }
        return ResultUtil.success(result);
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/logout")
    public Object logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return ResultUtil.success();
    }

}
