package com.hex.car.controller;

import com.hex.car.domain.Advertising;
import com.hex.car.domain.Brand;
import com.hex.car.service.AdvertisingService;
import com.hex.car.service.BrandService;
import com.hex.car.service.ModelService;
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

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

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
     *
     * @return
     */
    @GetMapping(value = "/toBrandAdd")
    public String toBrandAdd() {
        return "/brand/brandAdd";
    }

    /**
     * 跳转型号添加
     *
     * @param brandId 品牌id
     * @param model
     * @return
     */
    @GetMapping(value = "/toModelAdd")
    public String toModelAdd(String brandId, Model model) {
        model.addAttribute("brandId", brandId);
        return "/model/modelAdd";
    }

    /**
     * 跳转品牌修改
     *
     * @param brandId 品牌id
     * @param model
     * @return
     */
    @GetMapping(value = "/toBrandUpdate")
    public String toBrandUpdate(String brandId, Model model) {
        Brand brand = brandService.findBrandById(brandId);
        model.addAttribute("brand", brand);
        return "/brand/brandUpdate";
    }

    /**
     * 跳转型号修改
     *
     * @param modelId 型号id
     * @param model
     * @return
     */
    @GetMapping(value = "/toModelUpdate")
    public String toModelUpdate(String modelId, Model model) {
        com.hex.car.domain.Model m = modelService.findModelById(modelId);
        model.addAttribute("model", m);
        return "/model/modelUpdate";
    }

    /**
     * 跳转4s店列表页
     *
     * @return
     */
    @GetMapping(value = "/toShopList")
    public String toShopList() {
        return "/shop/shopList";
    }

    /**
     * 跳转添加4s店页
     *
     * @return
     */
    @GetMapping(value = "/toShopAdd")
    public String toShopAdd() {
        return "/shop/shopAdd";
    }

    /**
     * 跳转广告列表页
     *
     * @return
     */
    @GetMapping(value = "/toAdvertisingList")
    public String toAdvertisingList() {
        return "/advertising/advertisingList";
    }

    /**
     * 跳转添加广告页
     *
     * @return
     */
    @GetMapping(value = "/toAdvertisingAdd")
    public String toAdvertisingAdd() {
        return "/advertising/advertisingAdd";
    }

    /**
     * 跳转广告修改
     *
     * @param advertisingId 广告id
     * @param model
     * @return
     */
    @GetMapping(value = "/toAdvertisingUpdate")
    public String toAdvertisingUpdate(String advertisingId, Model model) {
        Advertising advertising = advertisingService.findAdvertisingById(advertisingId);
        model.addAttribute("advertising", advertising);
        return "/advertising/advertisingAdd";
    }

//    @GetMapping(value = "/toAdvertisingView/{id}")
//    public String toAdvertisingView(@PathVariable("id") String id, Model model) {
//        model.addAttribute("ad", advertisingService.findAdvertisingById(id));
//        return "/advertising/advertisingView";
//    }
}
