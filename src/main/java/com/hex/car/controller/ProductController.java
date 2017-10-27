package com.hex.car.controller;

import com.hex.car.domain.User;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.ProductService;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * User: hexuan
 * Date: 2017/10/25
 * Time: 下午2:52
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 根据名称查询启用商品集合
     *
     * @param name 商品名称
     * @return
     */
    public Object getUsingProductListByName(String name) {
        if (null == name) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(productService.findProductsByNameLikeAndStateOrderByName(name, new Integer(2)));
    }

    /**
     * 根据商品名称，登录人身份，查询启用商品集合
     *
     * @param name    商品名称
     * @param request request获取登录账号
     * @return
     */
    public Object getUsingProductListByNameAndIdentity(String name, HttpServletRequest request) {
        Object object = request.getSession().getAttribute("user");
        if (null == object) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        User user = (User) object;
        if (null == name) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (user.getId().equals("root")) {
            return ResultUtil.success(productService.findProductsByNameLikeAndStateOrderByName(name, new Integer(2)));
        } else if (null != user.getShop()) {
            return ResultUtil.success(productService.findProductsByNameLikeAndStateAndShopOrderByName(name, new Integer(2), user.getShop()));
        } else {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
    }
}
