package com.hex.car.controller;

import com.hex.car.domain.Brand;
import com.hex.car.domain.ImgBrand;
import com.hex.car.domain.ImgUser;
import com.hex.car.domain.User;
import com.hex.car.service.BrandService;
import com.hex.car.service.UserService;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/11/23
 * Time: 上午9:58
 */
@RestController
public class DebugController {

    @Autowired
    private UserService userService;

    @Autowired
    private BrandService brandService;

    @GetMapping("/debug/defaultImgUser")
    public Object defaultImgUser() {
        List<User> users = userService.findAllUser(new Sort(Sort.Direction.ASC, "createTime"));
        for (User user : users) {
            if (null == user.getImgUser()) {
                user.setImgUser(new ImgUser());
                userService.saveUser(user);
            }
        }
        return ResultUtil.success();
    }

    @GetMapping("/debug/defaultBrandUser")
    public Object defaultBrandUser() {
        List<Brand> brands = brandService.findAllBrandList();
        for (Brand brand : brands) {
            if (null == brand.getImgBrand()) {
                ImgBrand imgBrand = new ImgBrand();
                brand.setImgBrand(imgBrand);
                brandService.saveBrand(brand);
            }
        }
        return ResultUtil.success();
    }
}
