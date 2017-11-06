package com.hex.car.controller;

import com.hex.car.service.ParameterService;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${parameter.engineTypeCode}")
    private String engineTypeCode;

    @Value("${parameter.drivetrainCode}")
    private String drivetrainCode;

    @Value("${parameter.transmissionCode}")
    private String transmissionCode;

    @Value("${parameter.fuelTypeCode}")
    private String fuelTypeCode;

    @Value("${parameter.bodyTypeCode}")
    private String bodyTypeCode;

    @Value("${parameter.seatsCode}")
    private String seatsCode;

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

    /**
     * 获取进气形式集合（前台页面用）
     *
     * @return
     */
    @GetMapping(value = "/front/getEngineTypeList")
    public Object getEngineTypeList() {
        return ResultUtil.success(parameterService.findFirstByCode(engineTypeCode).getUsingChilds());
    }

    /**
     * 获取驱动方式集合（前台页面用）
     *
     * @return
     */
    @GetMapping(value = "/front/getDrivetrainList")
    public Object getDrivetrainList() {
        return ResultUtil.success(parameterService.findFirstByCode(drivetrainCode).getUsingChilds());
    }

    /**
     * 获取变速箱集合（前台页面用）
     *
     * @return
     */
    @GetMapping(value = "/front/getTransmissionList")
    public Object getTransmissionList() {
        return ResultUtil.success(parameterService.findFirstByCode(transmissionCode).getUsingChilds());
    }

    /**
     * 获取能源集合（前台页面用）
     *
     * @return
     */
    @GetMapping(value = "/front/getFuelTypeList")
    public Object getFuelTypeList() {
        return ResultUtil.success(parameterService.findFirstByCode(fuelTypeCode).getUsingChilds());
    }

    /**
     * 获取结构集合（前台页面用）
     *
     * @return
     */
    @GetMapping(value = "/front/getBodyTypeList")
    public Object getBodyTypeList() {
        return ResultUtil.success(parameterService.findFirstByCode(bodyTypeCode).getUsingChilds());
    }

    /**
     * 获取座位数集合（前台页面用）
     *
     * @return
     */
    @GetMapping(value = "/front/getSeatsList")
    public Object getSeatsList() {
        return ResultUtil.success(parameterService.findFirstByCode(seatsCode).getUsingChilds());
    }

}
