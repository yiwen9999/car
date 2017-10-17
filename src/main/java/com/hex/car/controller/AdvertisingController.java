package com.hex.car.controller;

import com.hex.car.domain.Advertising;
import com.hex.car.service.AdvertisingService;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 广告相关controller
 * User: hexuan
 * Date: 2017/9/29
 * Time: 下午3:00
 */
@RestController
public class AdvertisingController {

    @Autowired
    private AdvertisingService advertisingService;

    @PostMapping(value = "/saveAdvertising")
    public Object saveAdvertising(Advertising advertising) {

        return ResultUtil.success(advertisingService.saveAdvertising(advertising));
    }
}
