package com.hex.car.controller;

import com.hex.car.domain.CarType;
import com.hex.car.domain.ImgCarType;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.CarTypeService;
import com.hex.car.utils.FileUtil;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * User: hexuan
 * Date: 2017/11/4
 * Time: 下午2:33
 */
@RestController
public class CarTypeController {
    @Autowired
    private CarTypeService carTypeService;

    @Value("${web.upload-path}")
    private String path;

    @GetMapping(value = "/getAllCarTypeList")
    public Object getAllCarTypeList() {
        return ResultUtil.success(carTypeService.findAllCarTypeList());
    }

    /**
     * 保存车型对应图片
     *
     * @param carTypeId 车型id
     * @param file      车型对应图片
     * @return
     */
    @PostMapping(value = "/saveImgCarType")
    public Object saveImgCarType(String carTypeId,
                                 @RequestParam(value = "file") MultipartFile file) {
        if (null == file) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (carTypeId == null || carTypeId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        CarType carType = carTypeService.findCarTypeById(carTypeId);
        if (null == carType) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        ImgCarType imgCarType;
        try {
            FileUtil.uploadFile(file.getBytes(), path, fileName);
            imgCarType = new ImgCarType();
            imgCarType.setFileName(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
        }
        carType.setImgCarType(imgCarType);
        return ResultUtil.success(carTypeService.saveCarType(carType));
    }

}
