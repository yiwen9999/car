package com.hex.car.controller;

import com.hex.car.domain.Advertising;
import com.hex.car.domain.Brand;
import com.hex.car.domain.Evaluate;
import com.hex.car.service.*;
import com.hex.car.utils.HexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EvaluateService evaluateService;

    /**
     * 默认
     *
     * @return
     */
    @GetMapping(value = "/")
    public String main() {
        return "redirect:/toFrontMain";
    }

    /**
     * 后台主页
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("user", HexUtil.getUser(request));
        return "index";
    }

    /**
     * 后台登录
     *
     * @return
     */
    @GetMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 跳转修改密码
     *
     * @return
     */
    @GetMapping(value = "/toPasswordUpdate")
    public String toPasswordUpdate() {
        return "/passwordUpdate";
    }

    /**
     * 前台主页
     *
     * @return
     */
    @GetMapping(value = "/toFrontMain")
    public String toFrontMain() {
        return "/front/homepage";
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
     * 跳转品牌图片维护页面
     *
     * @return
     */
    @GetMapping(value = "/toImgBrandAdd")
    public String toImgBrandAdd(String brandId, Model model) {
        model.addAttribute("brandId", brandId);
        return "/brand/brandImg";
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
     * 跳转修改4s店页
     *
     * @return
     */
    @GetMapping(value = "/toShopUpdate")
    public String toShopUpdate(String id, Model model) {
        model.addAttribute("shop", shopService.findShopById(id));
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

    /**
     * 跳转文章列表页
     *
     * @return
     */
    @GetMapping(value = "/toEvaluateList")
    public String toEvaluateList(HttpServletRequest request, Model model) {
        model.addAttribute("user", HexUtil.getUser(request));
        return "/evaluate/evaluateList";
    }

    /**
     * 跳转添加文章页
     *
     * @return
     */
    @GetMapping(value = "/toEvaluateAdd")
    public String toEvaluateAdd(HttpServletRequest request, Model model) {
        model.addAttribute("user", HexUtil.getUser(request));
        return "/evaluate/evaluateAdd";
    }

    /**
     * 跳转文章浏览页
     *
     * @return
     */
    @GetMapping(value = "/toEvaluateView")
    public String toEvaluateView(String id, Model model) {
        model.addAttribute("id", id);
        return "/evaluate/evaluateView";
    }

    /**
     * 跳转文章选择对应商品页面
     *
     * @param id    文章id
     * @param model
     * @return
     */
    @GetMapping(value = "/toEvaluateChooseProduct")
    public String toEvaluateChooseProduct(String id, Model model) {
        model.addAttribute("id", id);
        Evaluate evaluate = evaluateService.findEvaluateById(id);
        model.addAttribute("productList", evaluate.getProducts());
        return "/evaluate/evaluateChooseProduct";
    }

    /**
     * 跳转商品列表
     *
     * @return
     */
    @GetMapping(value = "/toProductList")
    public String toProductList(HttpServletRequest request, Model model) {
        model.addAttribute("user", HexUtil.getUser(request));
        return "/product/productList";
    }

    /**
     * 跳转商品添加
     *
     * @return
     */
    @GetMapping(value = "/toProductAdd")
    public String toProductAdd(HttpServletRequest request, Model model) {
        model.addAttribute("user", HexUtil.getUser(request));
        return "/product/productAdd";
    }

    /**
     * 跳转修改商品页
     *
     * @return
     */
    @GetMapping(value = "/toProductUpdate")
    public String toProductUpdate(HttpServletRequest request, String id, Model model) {
        model.addAttribute("product", productService.findProductById(id));
        model.addAttribute("user", HexUtil.getUser(request));
        return "/product/productAdd";
    }

    /**
     * 跳转商品浏览
     *
     * @return
     */
    @GetMapping(value = "/toProductView")
    public String toProductView() {
        return "/product/productView";
    }

    /**
     * 跳转车型列表
     *
     * @return
     */
    @GetMapping(value = "/toCarTypeList")
    public String toCarTypeList() {
        return "/carType/carTypeList";
    }

    /**
     * 跳转车型图片维护页面
     *
     * @return
     */
    @GetMapping(value = "/toImgCarTypeAdd")
    public String toImgCarTypeAdd(String id, Model model) {
        model.addAttribute("id", id);
        return "/carType/carTypeImg";
    }

}
