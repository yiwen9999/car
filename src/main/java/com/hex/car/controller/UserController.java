package com.hex.car.controller;

import com.hex.car.domain.User;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.UserService;
import com.hex.car.utils.HexUtil;
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
//        try {
//            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//            token.setRememberMe(true);
//            Subject subject = SecurityUtils.getSubject();
//            subject.login(token);
//            return ResultUtil.success();
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//            return ResultUtil.error(ResultEnum.ERROR_LOGIN.getCode(), ResultEnum.ERROR_LOGIN.getMsg());
//        }
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
        return ResultUtil.success(user);
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

    /**
     * 保存用户
     *
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/saveUser")
    public Object saveUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setPassword(Md5SaltTool.getEncryptedPwd(user.getPassword()));
        return ResultUtil.success(userService.saveUser(user));
    }

    /**
     * 修改用户密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param request     request获取当前用户
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/updatePassword")
    public Object updatePassword(String oldPassword, String newPassword, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (Md5SaltTool.validPassword(oldPassword, user.getPassword())) {
            user.setPassword(Md5SaltTool.getEncryptedPwd(newPassword));
            return ResultUtil.success(userService.saveUser(user));
        } else {
            return ResultUtil.error(ResultEnum.ERROR_PASSWORD.getCode(), ResultEnum.ERROR_PASSWORD.getMsg());
        }
    }

    /**
     * 重置密码
     *
     * @param id 账号id
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/resetPassword")
    public Object resetPassword(String id) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (null == id || "".equals(id)) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        User user = userService.findUserById(id);
        if (null == user) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        user.setPassword(Md5SaltTool.getEncryptedPwd("000000"));
        return ResultUtil.success(userService.saveUser(user));
    }

}
