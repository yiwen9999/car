package com.hex.car.controller;

import com.hex.car.domain.Brand;
import com.hex.car.domain.ImgBrand;
import com.hex.car.domain.Model;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.BrandService;
import com.hex.car.service.ImgBrandService;
import com.hex.car.service.ModelService;
import com.hex.car.utils.FileUtil;
import com.hex.car.utils.HexUtil;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

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

    @Autowired
    private ImgBrandService imgBrandService;

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.zip-file-limit}")
    private Long zipFileLimit;

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
        if (null == brandId || brandId.equals("")) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Brand brand = brandService.findBrandById(brandId);
        if (null == brand) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(brand.getModels());
    }

    /**
     * 获取在用品牌集合，按首字母，名称排序
     *
     * @return
     */
    @GetMapping(value = "/getUsingBrandList")
    public Object getUsingBrandList() {
        return ResultUtil.success(brandService.findBrandListByState(new Integer(2)));
    }

    /**
     * 根据品牌id，获取在用型号集合
     *
     * @param brandId 品牌id
     * @return
     */
    @PostMapping(value = "/getUsingModelListByBrandId")
    public Object getUsingModelListByBrandId(String brandId) {
        if (null == brandId || brandId.equals("")) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Brand brand = brandService.findBrandById(brandId);
        if (null == brand) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(brand.getUsingModels());
    }

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
        if (!HexUtil.validateString(brandName)) {
            ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "品牌名称" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(brandInitial)) {
            ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "品牌首字母" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        Brand brand = new Brand();
        brand.setName(brandName);
        brand.setInitial(brandInitial);
        ImgBrand imgBrand = new ImgBrand();
        brand.setImgBrand(imgBrand);
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
        if (null == brandId || brandId.equals("")) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
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
     * 停启用品牌
     *
     * @param brandId 品牌id
     * @return
     */
    @PostMapping(value = "/updateBrandState")
    public Object updateBrandState(String brandId) {
        if (null == brandId || brandId.equals("")) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Brand brand = brandService.findBrandById(brandId);
        if (null == brand) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (brand.getState() == 2) {
            brand.setState(new Integer(-1));
        } else {
            brand.setState(new Integer(2));
        }
        return ResultUtil.success(brandService.saveBrand(brand));
    }

    /**
     * 停启用型号
     *
     * @param modelId 型号id
     * @return
     */
    @PostMapping(value = "/updateModelState")
    public Object updateModelState(String modelId) {
        if (null == modelId || modelId.equals("")) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Model model = modelService.findModelById(modelId);
        if (null == model) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (model.getState() == 2) {
            model.setState(new Integer(-1));
        } else {
            model.setState(new Integer(2));
        }
        return ResultUtil.success(modelService.saveModel(model));
    }

    /**
     * 更新品牌
     *
     * @param brandId      品牌id
     * @param brandName    品牌名称
     * @param brandInitial 品牌首字母
     * @return
     */
    @PostMapping(value = "/updateBrand")
    public Object updateBrand(String brandId, String brandName, String brandInitial) {
        if (null == brandId || brandId.equals("") || brandName == null || brandName.equals("") || brandInitial == null || brandInitial.equals("")) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Brand brand = brandService.findBrandById(brandId);
        if (null == brand) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        brand.setName(brandName);
        brand.setInitial(brandInitial);
        return ResultUtil.success(brandService.saveBrand(brand));
    }

    /**
     * 更新型号
     *
     * @param modelId   型号id
     * @param modelName 型号名称
     * @return
     */
    @PostMapping(value = "/updateModel")
    public Object updateModel(String modelId, String modelName) {
        if (null == modelId || modelId.equals("") || null == modelName || modelName.equals("")) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Model model = modelService.findModelById(modelId);
        if (null == model) {
            ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        model.setName(modelName);
        return ResultUtil.success(modelService.saveModel(model));
    }

    /**
     * 保存品牌对应图片
     *
     * @param brandId 品牌id
     * @param file    品牌对应图片
     * @return
     */
    @PostMapping(value = "/saveImgBrand")
    public Object saveImgBrand(String brandId,
                               @RequestParam(value = "file") MultipartFile file) {
        if (null == file) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (brandId == null || brandId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Brand brand = brandService.findBrandById(brandId);
        if (null == brand) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        ImgBrand imgBrand;
        try {
            FileUtil.uploadImgFile(file, path, fileName, zipFileLimit);
            imgBrand = new ImgBrand();
            imgBrand.setFileName(fileName);

            ImgBrand oldImgBrand = brand.getImgBrand();
            if (null != oldImgBrand) {
                if (oldImgBrand.getFileName().indexOf("default") < 0) {
                    File deleteFile = new File(path + oldImgBrand.getFileName());
                    if (deleteFile.exists()) {
                        deleteFile.delete();
                    }
                }
                imgBrandService.deleteImgBrand(oldImgBrand);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
        }
        brand.setImgBrand(imgBrand);
        return ResultUtil.success(brandService.saveBrand(brand));
    }

    /**
     * 【方法】循环保存型号
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
