package com.hex.car.controller;

import com.hex.car.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 下午2:24
 */
@Controller
public class PageController {

    @Autowired
    private AdvertisingService advertisingService;

    @GetMapping(value = "/toCarInfo")
    public String toCarInfo() {
        return "carInfo";
    }

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/index2")
    public String index2() {
        return "/index2";
    }

    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }

    @GetMapping(value = "/test2")
    public String test2() {
        return "test2";
    }

    /**
     * 跳转品牌列表
     *
     * @return
     */
    @GetMapping(value = "/toBrandList")
    public String toBrandList() {
        return "/brand/brandList";
    }

    /**
     * 跳转品牌添加页面
     * @return
     */
    @GetMapping(value = "/toBrandAdd")
    public String toBrandAdd() {
        return "/brand/brandAdd";
    }

    @GetMapping(value = "/toShopList")
    public String toShopList() {
        return "/shop/shopList";
    }

    @GetMapping(value = "/toShopAdd")
    public String toShopAdd() {
        return "/shop/shopAdd";
    }

    @GetMapping(value = "/toAdvertisingView/{id}")
    public String toAdvertisingView(@PathVariable("id") String id, Model model) {
        model.addAttribute("ad", advertisingService.findAdvertisingById(id));
        return "/advertising/advertisingView";
    }
}
