package com.hex.car.controller;

import com.hex.car.domain.ImgUser;
import com.hex.car.domain.Personnel;
import com.hex.car.domain.User;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.PersonnelService;
import com.hex.car.service.UserService;
import com.hex.car.utils.HexUtil;
import com.hex.car.utils.Md5SaltTool;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: hexuan
 * Date: 2017/10/17
 * Time: 上午11:35
 */
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonnelService personnelService;

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
            if (null != user && 2 == user.getState()) {
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
        } else {
            return ResultUtil.error(ResultEnum.ERROR_LOGIN.getCode(), ResultEnum.ERROR_LOGIN.getMsg());
        }
        return ResultUtil.success(user);
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/logout")
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
        user.setImgUser(new ImgUser());
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

    /**
     * 忘记密码 设置新密码
     *
     * @param username
     * @param password
     * @param smsCode
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/newPassword")
    public Object newPassword(String username, String password, String smsCode, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (null == username || username.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (null == password || password.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (null == smsCode || smsCode.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }

        String cPhone = "", cSmsCode = "";
        if (null != request.getSession().getAttribute("car_phone")) {
            cPhone = (String) request.getSession().getAttribute("car_phone");
        }
        if (null != request.getSession().getAttribute("car_smsCode")) {
            cSmsCode = (String) request.getSession().getAttribute("car_smsCode");
        }
        if (!cPhone.equals(username) || !cSmsCode.equals(smsCode)) {
            return ResultUtil.error(ResultEnum.ERROR_SMSCODE.getCode(), ResultEnum.ERROR_SMSCODE.getMsg());
        }

        User user = userService.findUserByUsername(username);
        if (null == user) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        user.setPassword(Md5SaltTool.getEncryptedPwd(password));
        userService.saveUser(user);
        request.getSession().setAttribute("user", user);
        return ResultUtil.success(user);
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param name     姓名
     * @param nickname 昵称
     * @param mobile   手机号
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/register")
    public Object register(String username, String password, String name, String nickname, String mobile, String smsCode, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (null == username || username.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (null == password || password.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (null == mobile || mobile.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (null == smsCode || smsCode.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }

        String cPhone = "", cSmsCode = "";
        if (null != request.getSession().getAttribute("car_phone")) {
            cPhone = (String) request.getSession().getAttribute("car_phone");
        }
        if (null != request.getSession().getAttribute("car_smsCode")) {
            cSmsCode = (String) request.getSession().getAttribute("car_smsCode");
        }
        if (!cPhone.equals(mobile) || !cSmsCode.equals(smsCode)) {
            return ResultUtil.error(ResultEnum.ERROR_SMSCODE.getCode(), ResultEnum.ERROR_SMSCODE.getMsg());
        }

        Personnel p = personnelService.findFirstPersonnelByMobile(mobile);
        if (null != p) {
            return ResultUtil.error(ResultEnum.ERROR_MOBILE.getCode(), ResultEnum.ERROR_MOBILE.getMsg());
        }
        User u = userService.findUserByUsername(username);
        if (null != u) {
            return ResultUtil.error(ResultEnum.ERROR_USERNAME.getCode(), ResultEnum.ERROR_USERNAME.getMsg());
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(Md5SaltTool.getEncryptedPwd(password));
        Personnel personnel = new Personnel();
        personnel.setName(name);
        personnel.setNickname(nickname);
        personnel.setMobile(mobile);
        personnel.setUser(user);
        user.setPersonnel(personnel);
        user.setImgUser(new ImgUser());
        userService.saveUser(user);

        request.getSession().setAttribute("user", user);

        return ResultUtil.success(user);
    }

    /**
     * 获取账号信息
     *
     * @return
     */
    @PostMapping(value = "/getUserInfo")
    public Object getUserInfo(HttpServletRequest request) {
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("personnel", user.getPersonnel());
        return ResultUtil.success(map);
    }

    /**
     * 校验注册信息
     *
     * @param username 登录名
     * @param mobile   手机号
     * @return
     */
    @PostMapping(value = "/validateRegister")
    public Object validateRegister(String username, String mobile) {
        if (null != username && !"".equals(username)) {
            User u = userService.findUserByUsername(username);
            if (null != u) {
                return ResultUtil.error(ResultEnum.ERROR_USERNAME.getCode(), ResultEnum.ERROR_USERNAME.getMsg());
            }
        }
        if (null != mobile && !"".equals(mobile)) {
            Personnel p = personnelService.findFirstPersonnelByMobile(mobile);
            if (null != p) {
                return ResultUtil.error(ResultEnum.ERROR_MOBILE.getCode(), ResultEnum.ERROR_MOBILE.getMsg());
            }
        }
        return ResultUtil.success();
    }

}
