package com.hex.car.controller;

import com.hex.car.domain.CarType;
import com.hex.car.service.CarTypeService;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/11/4
 * Time: 下午2:33
 */
@RestController
public class CarTypeController {
    @Autowired
    private CarTypeService carTypeService;

    /**
     * 获取车型集合（前台页面用）
     *
     * @return
     */
    @GetMapping(value = "/front/getCarTypeList")
    public Object getCarTypeListForFront() {
        List<CarType> carTypeList = carTypeService.findCarTypesByStateOrderBySort(new Integer(2));
        return ResultUtil.success(carTypeList);
    }
}
