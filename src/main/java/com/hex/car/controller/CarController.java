package com.hex.car.controller;

import com.hex.car.domain.Car;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.*;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: hexuan
 * Date: 2017/10/13
 * Time: 下午2:26
 */
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private CarTypeService carTypeService;

    @Autowired
    private ParameterService parameterService;

    /**
     * 保存车辆
     *
     * @param car            车辆
     * @param brandId        品牌id
     * @param modelId        型号id
     * @param carTypeId      车型id
     * @param engineTypeId   进气形式id
     * @param drivetrainId   驱动方式id
     * @param transmissionId 变速箱id
     * @param fuelTypeId     能源id
     * @param bodyTypeId     结构id
     * @param seatsId        座位数id
     * @return
     */
    @PostMapping(value = "/saveCar")
    public Object saveCar(Car car,
                          String brandId,
                          String modelId,
                          String carTypeId,
                          String engineTypeId,
                          String drivetrainId,
                          String transmissionId,
                          String fuelTypeId,
                          String bodyTypeId,
                          String seatsId) {
        if (brandId == null || brandId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (modelId == null || modelId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (carTypeId == null || carTypeId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (engineTypeId == null || engineTypeId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (drivetrainId == null || drivetrainId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (transmissionId == null || transmissionId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (fuelTypeId == null || fuelTypeId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (bodyTypeId == null || bodyTypeId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (seatsId == null || seatsId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        car.setBrand(brandService.findBrandById(brandId));
        car.setModel(modelService.findModelById(modelId));
        car.setCarType(carTypeService.findCarTypeById(carTypeId));
        car.setEngineType(parameterService.findParameterById(engineTypeId));
        car.setDrivetrain(parameterService.findParameterById(drivetrainId));
        car.setTransmission(parameterService.findParameterById(transmissionId));
        car.setFuelType(parameterService.findParameterById(fuelTypeId));
        car.setBodyType(parameterService.findParameterById(bodyTypeId));
        car.setSeats(parameterService.findParameterById(seatsId));
        return ResultUtil.success(carService.saveCar(car));
    }

    /**
     * 获取车辆信息
     *
     * @param id 车辆id
     * @return
     */
    @GetMapping(value = "/getCarInfo")
    public Object getCarInfo(String id) {
        return ResultUtil.success(carService.findCarById(id));
    }

    /**
     * 获取全部车辆库集合
     *
     * @return
     */
    @GetMapping(value = "/getAllCarList")
    public Object getAllCarList() {
        return ResultUtil.success(carService.findAllCar());
    }

    /**
     * 按名称查询在用车辆集合
     *
     * @param name 名称
     * @return
     */
    @PostMapping(value = "/searchUsingCarListByName")
    public Object searchUsingCarListByName(String name) {
        if (name == null || name.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(carService.findCarsByNameLikeAndStateOrderByName(name, new Integer(2)));
    }

    /**
     * 删除车辆
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteCar")
    public Object deleteCar(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Car car = carService.findCarById(id);
        if (null == car) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        carService.deleteCar(car);
        return ResultUtil.success();
    }

}
