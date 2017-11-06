package com.hex.car.config;

import com.hex.car.domain.User;
import com.hex.car.service.UserService;
import com.hex.car.utils.Md5SaltTool;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * User: hexuan
 * Date: 2017/11/3
 * Time: 上午10:31
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        User user = (User) principals.getPrimaryPrincipal();
//        if (null != user.getShop()) {
//            authorizationInfo.addRole("shop");
//            authorizationInfo.addStringPermission("");
//        } else if (null != user.getPersonnel()) {
//            authorizationInfo.addRole("user");
//            authorizationInfo.addStringPermission("");
//        } else if (user.getId().equals("root")) {
//            authorizationInfo.addRole("admin");
//            authorizationInfo.addStringPermission("");
//        }
//        return authorizationInfo;
        return null;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        System.out.println("身份认证方法：MyShiroRealm.doGetAuthenticationInfo()");

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        System.out.println("token.getUsername:  " + token.getUsername());
        System.out.println("token.getPassword:  " + token.getPassword().toString());
        System.out.println("token.getPassword:  " + String.valueOf(token.getPassword()));

        User user = userService.findUserByUsername(token.getUsername());
        boolean passwordTrue = false;
        try {
            passwordTrue = Md5SaltTool.validPassword(String.valueOf(token.getPassword()), user.getPassword());
            System.out.println(passwordTrue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!passwordTrue) {
            throw new AccountException("帐号或密码不正确！");
        } else if (user.getState() != 2) {
            /**
             * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
             */
            throw new DisabledAccountException("帐号已经停用");
        }
        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
    }
}
