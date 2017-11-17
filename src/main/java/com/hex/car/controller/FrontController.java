package com.hex.car.controller;

import com.hex.car.domain.*;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.*;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前端相关接口
 * <p>
 * User: hexuan
 * Date: 2017/11/10
 * Time: 下午5:06
 */
@RestController
public class FrontController {

    @Autowired
    private AdvertisingService advertisingService;

    @Autowired
    private CarTypeService carTypeService;

    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private ShopService shopService;

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
     * 获取在用广告集合
     *
     * @return
     */
    @PostMapping(value = "/front/getAdvertisingList")
    public Object getAdvertisingListForFront() {
        return ResultUtil.success(advertisingService.findAdvertisingsByStateOrderBySort(new Integer(2)));
    }

    /**
     * 获取车型集合
     *
     * @return
     */
    @PostMapping(value = "/front/getCarTypeList")
    public Object getCarTypeListForFront() {
        List<CarType> carTypeList = carTypeService.findCarTypesByStateOrderBySort(new Integer(2));
        return ResultUtil.success(carTypeList);
    }

    /**
     * 获取4个最新文章集合（准备废弃）
     *
     * @return
     */
    @PostMapping("/front/get4EvaluateList")
    public Object get4EvaluateList() {
        List<Evaluate> evaluates = evaluateService.findTop4ByStateOrderByCreateTimeDesc(new Integer(2));
        return ResultUtil.success(evaluates);
    }

    /**
     * 搜索文章
     *
     * @param page    页数（从0开始）
     * @param size    每页数量
     * @param sortStr 排序（[title]按名称 or [createTime]按创建时间）
     * @param asc     排序方式（[asc]正序 or [desc]反序）
     * @return
     */
    @PostMapping(value = "/front/searchEvaluateList")
    public Object searchEvaluateListForFront(Integer page, Integer size, String sortStr, String asc) {
        Sort sort;
        if (asc.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, sortStr);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortStr);
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Map<String, Object> condition = new HashMap<>();
        condition.put("state", new Integer(2));
        return ResultUtil.success(evaluateService.findEvaluates(condition, pageRequest));
    }

    /**
     * 获取4个最新商品集合（准备废弃）
     *
     * @return
     */
    @PostMapping("/front/get4ProductList")
    public Object get4ProductList() {
        List<Product> products = productService.findTop4ByStateOrderByCreateTimeDesc(new Integer(2));
        return ResultUtil.success(products);
    }

    /**
     * 搜索在售车辆
     *
     * @param page           页数（从0开始）
     * @param size           每页数量
     * @param sortStr        排序（[name]按名称 or [price]按价格 or [createTime]按创建时间）
     * @param asc            排序方式（[asc]正序 or [desc]反序）
     * @param name
     * @param minPrice
     * @param maxPrice
     * @param year
     * @param brandId
     * @param modelId
     * @param carTypeId
     * @param placeId
     * @param engineTypeId
     * @param drivetrainId
     * @param transmissionId
     * @param fuelTypeId
     * @param bodyTypeId
     * @param seatsId
     * @return
     */
    @PostMapping(value = "/front/searchProductList")
    public Object searchProductListForFront(Integer page, Integer size, String sortStr, String asc,
                                            String name, Double minPrice, Double maxPrice, Integer year,
                                            String brandId, String modelId, String carTypeId, String placeId,
                                            String engineTypeId, String drivetrainId, String transmissionId,
                                            String fuelTypeId, String bodyTypeId, String seatsId, String shopId) {
        Sort sort;
        if (asc.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, sortStr);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortStr);
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Map<String, Object> condition = new HashMap<>();
        condition.put("name", name);
        condition.put("minPrice", minPrice);
        condition.put("maxPrice", maxPrice);
        condition.put("year", year);
        condition.put("brandId", brandId);
        condition.put("modelId", modelId);
        condition.put("carTypeId", carTypeId);
        condition.put("placeId", placeId);
        condition.put("engineTypeId", engineTypeId);
        condition.put("drivetrainId", drivetrainId);
        condition.put("transmissionId", transmissionId);
        condition.put("fuelTypeId", fuelTypeId);
        condition.put("bodyTypeId", bodyTypeId);
        condition.put("seatsId", seatsId);
        condition.put("shopId", shopId);
        return ResultUtil.success(productService.findProducts(condition, pageRequest));
    }

    /**
     * 获取在售车辆品牌集合
     *
     * @return
     */
    @PostMapping(value = "/front/getBrandList")
    public Object getBrandListForFront() {
        List<Brand> brandList = productService.findDistinctBrandByProduct(new Integer(2));
        return ResultUtil.success(brandList);
    }

    /**
     * 获取在售车辆型号集合
     *
     * @return
     */
    @PostMapping(value = "/front/getModelList")
    public Object getModelListForFront() {
        List<Model> modelList = productService.findDistinctModelByProduct(new Integer(2));
        return ResultUtil.success(modelList);
    }

    /**
     * 获取进气形式集合
     *
     * @return
     */
    @PostMapping(value = "/front/getEngineTypeList")
    public Object getEngineTypeList() {
        return ResultUtil.success(parameterService.findFirstByCode(engineTypeCode).getUsingChilds());
    }

    /**
     * 获取驱动方式集合
     *
     * @return
     */
    @PostMapping(value = "/front/getDrivetrainList")
    public Object getDrivetrainList() {
        return ResultUtil.success(parameterService.findFirstByCode(drivetrainCode).getUsingChilds());
    }

    /**
     * 获取变速箱集合
     *
     * @return
     */
    @PostMapping(value = "/front/getTransmissionList")
    public Object getTransmissionList() {
        return ResultUtil.success(parameterService.findFirstByCode(transmissionCode).getUsingChilds());
    }

    /**
     * 获取能源集合
     *
     * @return
     */
    @PostMapping(value = "/front/getFuelTypeList")
    public Object getFuelTypeList() {
        return ResultUtil.success(parameterService.findFirstByCode(fuelTypeCode).getUsingChilds());
    }

    /**
     * 获取结构集合
     *
     * @return
     */
    @PostMapping(value = "/front/getBodyTypeList")
    public Object getBodyTypeList() {
        return ResultUtil.success(parameterService.findFirstByCode(bodyTypeCode).getUsingChilds());
    }

    /**
     * 获取座位数集合
     *
     * @return
     */
    @PostMapping(value = "/front/getSeatsList")
    public Object getSeatsList() {
        return ResultUtil.success(parameterService.findFirstByCode(seatsCode).getUsingChilds());
    }

    /**
     * 获取北京下级地域集合
     *
     * @return
     */
    @PostMapping(value = "/front/getPlaceList")
    public Object getPlaceListForFront() {
        Place bjPlace = placeService.findPlaceById("110100");
        if (null == bjPlace) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        List<Place> placeList = bjPlace.getUsingChilds();
        return ResultUtil.success(placeList);
    }

    /**
     * 获取4个最新4s店集合（准备废弃）
     *
     * @return
     */
    @PostMapping("/front/get4ShopList")
    public Object get4ShopList() {
        List<Shop> shops = shopService.findTop4ByStateOrderByCreateTimeDesc(new Integer(2));
        return ResultUtil.success(shops);
    }

    /**
     * 搜索4s店
     *
     * @param page    页数（从0开始）
     * @param size    每页数量
     * @param sortStr 排序（[name]按名称 or [createTime]按创建时间）
     * @param asc     排序方式（[asc]正序 or [desc]反序）
     * @return
     */
    @PostMapping(value = "/front/searchShopList")
    public Object searchShopListForFront(Integer page, Integer size, String sortStr, String asc) {
        Sort sort;
        if (asc.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, sortStr);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortStr);
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Map<String, Object> condition = new HashMap<>();
        condition.put("state", new Integer(2));
        return ResultUtil.success(shopService.findShops(condition, pageRequest));
    }
}
