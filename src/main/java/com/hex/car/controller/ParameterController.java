package com.hex.car.controller;

import com.hex.car.service.ParameterService;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * User: hexuan
 * Date: 2017/10/30
 * Time: 下午3:51
 */
@RestController
public class ParameterController {

    @Autowired
    private ParameterService parameterService;

    /**
     * 获取所有在用参数集合，按排序号排序
     *
     * @return
     */
    @GetMapping(value = "/getUsingParameterList")
    public Object getUsingParameterList() {
        return ResultUtil.success(parameterService.findParametersByStateOrderBySort(new Integer(2)));
    }

    /**
     * 获取所有顶级参数集合和子集参数集合
     *
     * @return
     */
    @GetMapping(value = "/getTopAndChildParameterList")
    public Object getTopAndChildParameterList() {
        Map<String, Object> map = new HashMap<>();
        map.put("topList", parameterService.findTopParameterListOrderBySort());
        map.put("childList", parameterService.findChildParameterListOrderBySort());
        return ResultUtil.success(map);
    }

    /**
     * 获取所有在用顶级参数集合和子集参数集合
     *
     * @return
     */
    @GetMapping(value = "/getUsingTopAndChildParameterList")
    public Object getUsingTopAndChildParameterList() {
        Map<String, Object> map = new HashMap<>();
        map.put("topList", parameterService.findUsingTopParameterListOrderBySort(new Integer(2)));
        map.put("childList", parameterService.findUsingChildParameterListOrderBySort(new Integer(2)));
        return ResultUtil.success(map);
    }

}
