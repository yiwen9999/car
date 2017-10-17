package com.hex.car.controller;

import com.hex.car.domain.Brand;
import com.hex.car.domain.Model;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.BrandService;
import com.hex.car.service.ModelService;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌型号相关controller
 * User: hexuan
 * Date: 2017/9/23
 * Time: 下午2:37
 */
@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

    /**
     * 获取所有品牌集合，按首字母，名称排序
     *
     * @return
     */
    @GetMapping(value = "/getBrandAllList")
    public Object getBrandAllList() {
        return ResultUtil.success(brandService.findAllBrandList());
    }

    /**
     * 根据品牌id，获取型号集合
     *
     * @param brandId 品牌id
     * @return
     */
    @PostMapping(value = "/getModelListByBrandId")
    public Object getModelListByBrandId(String brandId) {
        return ResultUtil.success(brandService.findBrandById(brandId).getModels());
    }

    /**
     * 获取所有型号集合，按名称排序
     *
     * @return
     */
//    @GetMapping(value = "/getModelAllList")
//    public Object getModelAllList() {
//        return ResultUtil.success(modelService.findAllModelList());
//    }

    /**
     * 保存品牌
     *
     * @param brand 品牌
     * @return
     */
//    @PostMapping(value = "/saveBrand")
//    public Object saveBrand(Brand brand) {
//        return ResultUtil.success(brandService.saveBrand(brand));
//    }

    /**
     * 保存型号
     *
     * @param model   型号
     * @param brandId 品牌id
     * @return
     */
//    @PostMapping(value = "/saveModel")
//    public Object saveModel(Model model, String brandId) {
//        Brand brand = brandService.findBrandById(brandId);
//        if (null == brand) {
//            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
//        }
//        model.setBrand(brand);
//        return ResultUtil.success(modelService.saveModel(model));
//    }

    /**
     * 品牌，批量型号添加
     *
     * @param modelNames   型号名称集合
     * @param brandName    品牌名称
     * @param brandInitial 品牌首字母
     * @return
     */
    @PostMapping(value = "/saveBrandAndModels")
    public Object saveBrandAndModels(@RequestParam String[] modelNames,
                                     String brandName,
                                     String brandInitial) {
        Brand brand = new Brand();
        brand.setName(brandName);
        brand.setInitial(brandInitial);
        brandService.saveBrand(brand);
        if (null == modelNames || 0 == modelNames.length) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        saveModelByModelNamesAndBrand(modelNames, brand);
        return ResultUtil.success();
    }

    /**
     * 批量保存型号
     *
     * @param modelNames 型号名称集合
     * @param brandId    品牌id
     * @return
     */
    @PostMapping(value = "/saveModels")
    public Object saveModels(@RequestParam String[] modelNames, String brandId) {
        Brand brand = brandService.findBrandById(brandId);
        if (null == modelNames || 0 == modelNames.length) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (null == brand) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        saveModelByModelNamesAndBrand(modelNames, brand);
        return ResultUtil.success();
    }

    /**
     * 循环保存型号方法
     *
     * @param modelNames 型号名称集合
     * @param brand      品牌
     */
    private void saveModelByModelNamesAndBrand(String[] modelNames, Brand brand) {
        Model model;
        for (int i = 0; i < modelNames.length; i++) {
            model = new Model();
            model.setBrand(brand);
            model.setName(modelNames[i]);
            modelService.saveModel(model);
        }
    }
}
