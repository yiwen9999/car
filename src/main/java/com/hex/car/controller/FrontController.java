package com.hex.car.controller;

import com.hex.car.domain.*;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.*;
import com.hex.car.utils.Base64Util;
import com.hex.car.utils.FileUtil;
import com.hex.car.utils.HexUtil;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 前端相关接口
 * <p>
 * User: hexuan
 * Date: 2017/11/10
 * Time: 下午5:06
 */
@CrossOrigin
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

    @Autowired
    private BrowsingHistoryProductService browsingHistoryProductService;

    @Autowired
    private FavoritesProductService favoritesProductService;

    @Autowired
    private UserService userService;

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

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.zip-file-limit}")
    private Long zipFileLimit;

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

    @PostMapping(value = "/front/searchProductListForWeb")
    public Object searchProductListForFrontWeb(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size,
                                               @RequestParam(value = "sortStr", defaultValue = "createTime") String sortStr,
                                               @RequestParam(value = "asc", defaultValue = "desc") String asc,
                                               String name, Double minPrice, Double maxPrice, Integer[] years,
                                               String[] brandIds, String[] modelIds, String[] carTypeIds, String[] placeIds,
                                               String[] engineTypeIds, String[] drivetrainIds, String[] transmissionIds,
                                               String[] fuelTypeIds, String[] bodyTypeIds, String[] seatsIds, String[] shopIds,
                                               Double minDisplacement, Double maxDisplacement) {
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
        condition.put("years", years);
        condition.put("brandIds", brandIds);
        condition.put("modelIds", modelIds);
        condition.put("carTypeIds", carTypeIds);
        condition.put("placeIds", placeIds);
        condition.put("engineTypeIds", engineTypeIds);
        condition.put("drivetrainIds", drivetrainIds);
        condition.put("transmissionIds", transmissionIds);
        condition.put("fuelTypeIds", fuelTypeIds);
        condition.put("bodyTypeIds", bodyTypeIds);
        condition.put("seatsIds", seatsIds);
        condition.put("shopIds", shopIds);
        condition.put("minDisplacement", minDisplacement);
        condition.put("maxDisplacement", maxDisplacement);
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
    public Object getModelListForFront(String brandId) {
        List<Model> modelList;
        if (null != brandId && !"".equals(brandId)) {
            modelList = productService.findDistinctModelByProductAndBrandId(new Integer(2), brandId);
        } else {
            modelList = productService.findDistinctModelByProduct(new Integer(2));
        }
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

    @PostMapping(value = "/front/getShopInfo")
    public Object getShopInfoForFront(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Shop shop = shopService.findShopById(id);
        if (null == shop) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(shop);
    }

    /**
     * 获取车辆信息
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping(value = "/front/getProductInfo")
    public Object getProductInfoForFront(String id, HttpServletRequest request) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Product product = productService.findProductById(id);
        if (null == product) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        User user = HexUtil.getUser(request);
//        if (null == user) {
//            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
//        }
        if (null != user) {
            BrowsingHistoryProduct browsingHistoryProduct = new BrowsingHistoryProduct();
            browsingHistoryProduct.setProduct(product);
            browsingHistoryProduct.setUser(user);
            browsingHistoryProductService.saveBrowsingHistoryProduct(browsingHistoryProduct);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        Shop shop = new Shop();
        shop.setId(product.getShop().getId());
        shop.setName(product.getShop().getName());
        shop.setAddress(product.getShop().getAddress());
        shop.setLinkman(product.getShop().getLinkman());
        shop.setCustomService(product.getShop().getCustomService());
        shop.setEmail(product.getShop().getEmail());
        shop.setPhone(product.getShop().getPhone());
        shop.setLongitude(product.getShop().getLongitude());
        shop.setLatitude(product.getShop().getLatitude());
        shop.setMainImgShops(product.getShop().getMainImgShops());
        shop.setCommonImgShops(product.getShop().getCommonImgShops());
        shop.setBannerImgShops(product.getShop().getBannerImgShops());
        shop.setPlace(product.getShop().getPlace());
        shop.setUser(product.getShop().getUser());
        map.put("shop", shop);
        map.put("evaluateList", product.getUsingEvaluates());
        return ResultUtil.success(map);
    }

    /**
     * 获取最热车辆集合
     *
     * @param showNumber 显示个数
     * @return
     */
    @PostMapping(value = "/front/getProductListForHot")
    public Object getProductListForHot(@RequestParam(defaultValue = "4") Integer showNumber) {
        List<Product> productList = browsingHistoryProductService.findProductsByBrowsingHistoryCountDesc();
        if (showNumber < productList.size())
            productList = productList.subList(0, showNumber);
        return ResultUtil.success(productList);
    }

    /**
     * 获取促销车辆集合
     *
     * @param showNumber 显示个数
     * @return
     */
    @PostMapping(value = "/front/getProductListForPromotion")
    public Object getProductListForPromotion(@RequestParam(defaultValue = "4") Integer showNumber) {
        // TODO 目前未做促销功能，返回内容和最热相同
        List<Product> productList = browsingHistoryProductService.findProductsByBrowsingHistoryCountDesc();
        if (showNumber < productList.size())
            productList = productList.subList(0, showNumber);
        return ResultUtil.success(productList);
    }

    /**
     * 获取文章信息
     *
     * @param id 文章id
     * @return
     */
    @PostMapping(value = "/front/getEvaluateInfo")
    public Object getEvaluateInfoForFront(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Evaluate evaluate = evaluateService.findEvaluateById(id);
        if (null == evaluate) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Map<String, Object> map = new HashMap<>();
        Product product = null;
        if (!evaluate.getProducts().isEmpty()) {
            product = evaluate.getProducts().iterator().next();
        }
        Shop shop = null;
        if (null != product) {
            shop = new Shop();
            shop.setId(product.getShop().getId());
            shop.setName(product.getShop().getName());
            shop.setAddress(product.getShop().getAddress());
            shop.setLinkman(product.getShop().getLinkman());
            shop.setCustomService(product.getShop().getCustomService());
            shop.setEmail(product.getShop().getEmail());
            shop.setPhone(product.getShop().getPhone());
            shop.setLongitude(product.getShop().getLongitude());
            shop.setLatitude(product.getShop().getLatitude());
            shop.setMainImgShops(product.getShop().getMainImgShops());
            shop.setCommonImgShops(product.getShop().getCommonImgShops());
            shop.setBannerImgShops(product.getShop().getBannerImgShops());
            shop.setPlace(product.getShop().getPlace());
            shop.setUser(product.getShop().getUser());
        }
        map.put("shop", shop);
        map.put("evaluate", evaluate);
        map.put("product", product);
        return ResultUtil.success(map);
    }

    /**
     * 收藏
     *
     * @param id      productId
     * @param request
     * @return
     */
    @PostMapping(value = "/front/favorite")
    public Object saveFavorite(String id, HttpServletRequest request) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Product product = productService.findProductById(id);
        if (null == product) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        FavoritesProduct favoritesProduct = new FavoritesProduct();
        favoritesProduct.setProduct(product);
        favoritesProduct.setUser(user);
        return ResultUtil.success(favoritesProductService.saveFavoritesProduct(favoritesProduct));
    }

    /**
     * 取消收藏
     *
     * @param id      productId
     * @param request
     * @return
     */
    @PostMapping(value = "/front/unFavorite")
    public Object deleteFavorite(String id, HttpServletRequest request) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Product product = productService.findProductById(id);
        if (null == product) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        FavoritesProduct favoritesProduct = favoritesProductService.findFirstByUserAndProduct(user, product);
        if (null != favoritesProduct) {
            favoritesProductService.deleteFavoritesProduct(favoritesProduct);
        }
        return ResultUtil.success();
    }

    /**
     * 检查是否已收藏
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping(value = "/front/checkFavorite")
    public Object checkFavorite(String id, HttpServletRequest request) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Product product = productService.findProductById(id);
        if (null == product) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        return ResultUtil.success(favoritesProductService.findFirstByUserAndProduct(user, product));
    }

    /**
     * 我的收藏
     *
     * @param page
     * @param size
     * @param sortStr
     * @param asc
     * @param request
     * @return
     */
    @PostMapping(value = "/front/myFavorite")
    public Object myFavorite(Integer page, Integer size, String sortStr, String asc, HttpServletRequest request) {
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        if (null == page) {
            page = new Integer(0);
        }
        if (null == size) {
            size = new Integer(10);
        }
        if (null == sortStr || sortStr.equals("")) {
            sortStr = "createTime";
        }
        if (null == asc || asc.equals("")) {
            asc = "desc";
        }
        Sort sort;
        if (asc.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, sortStr);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortStr);
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", user.getId());
        return ResultUtil.success(favoritesProductService.findFavoritesProducts(condition, pageRequest));
    }

    /**
     * 我的推荐
     *
     * @param page
     * @param size
     * @param sortStr
     * @param asc
     * @param request
     * @return
     */
    @PostMapping(value = "/front/myRecommend")
    public Object myRecommend(Integer page, Integer size, String sortStr, String asc, HttpServletRequest request) {
        if (null == page) {
            page = new Integer(0);
        }
        if (null == size) {
            size = new Integer(10);
        }
        if (null == sortStr || sortStr.equals("")) {
            sortStr = "createTime";
        }
        if (null == asc || asc.equals("")) {
            asc = "desc";
        }
        Sort sort;
        if (asc.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, sortStr);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortStr);
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        User user = HexUtil.getUser(request);
//        if (null == user) {
//            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
//        }
        Product product;
        String carTypeId = null;
        Double price;
        Double minPrice = null, maxPrice = null;
        Map<String, Object> condition = new HashMap<>();
        /**
         * 推荐策略：根据当前用户最新浏览记录车辆的车型，该价格上下浮动5万元元作为区间，查询车辆。
         */
        if (null != user) {
            if (!user.getBrowsingHistoryCars().isEmpty()) {
                product = user.getBrowsingHistoryCars().iterator().next().getProduct();
                if (null != product) {
                    if (null != product.getCar() && null != product.getCar().getCarType()) {
                        carTypeId = product.getCar().getCarType().getId();
                    }
                    price = product.getPrice();
                    if (null != price) {
                        minPrice = price - 50000;
                        if (minPrice < 0) {
                            minPrice = new Double(0);
                        }
                        maxPrice = price + 50000;
                    }
                }
            }
            condition.put("minPrice", minPrice);
            condition.put("maxPrice", maxPrice);
            condition.put("carTypeId", carTypeId);
        }
        return ResultUtil.success(productService.findProducts(condition, pageRequest));
    }

    /**
     * 前台用户中心，修改头像，修改用户名
     *
     * @param file
     * @param username
     * @param request
     * @return
     */
    @PostMapping(value = "/front/editUser")
    public Object editUser(@RequestParam(value = "file", required = false) MultipartFile file, String username, HttpServletRequest request) {
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        ImgUser imgUser;
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        try {
            FileUtil.uploadImgFile(file, path, fileName, zipFileLimit);
            imgUser = new ImgUser();
            imgUser.setFileName(fileName);
            user.setImgUser(imgUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
        }
        if (null != username && !"".equals(username)) {
            user.setUsername(username);
        }
        return ResultUtil.success(userService.saveUser(user));
    }

    @PostMapping(value = "/front/updateUserImg")
    public Object updateUserImg(String imgStr, String imgType, HttpServletRequest request) {
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        user = userService.findUserById(user.getId());
        if (null != imgStr && !"".equals(imgStr) && null != imgType && !"".equals(imgType)) {
            String fileName = UUID.randomUUID() + "." + imgType;
            try {
                String oleFileName = user.getImgUser().getFileName();
                FileUtil.uploadImgFile64(Base64Util.GenerateImage(imgStr), path, fileName, zipFileLimit);
                user.getImgUser().setFileName(fileName);

                // 若之前头像不是默认头像，则删除老头像
                if (oleFileName.indexOf("default") < 0) {
                    File deleteFile = new File(path + oleFileName);
                    if (deleteFile.exists())
                        deleteFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        } else {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        userService.saveUser(user);
        request.getSession().setAttribute("user", user);
        return ResultUtil.success(user);
    }

    @PostMapping(value = "/front/updateUserName")
    public Object updateUserName(String username, HttpServletRequest request) {
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        if (null != username && !"".equals(username)) {
            user.setUsername(username);
        } else {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(userService.saveUser(user));
    }

    /**
     * 获取在售车辆价格区间
     *
     * @return
     */
    @PostMapping(value = "/front/getPriceArea")
    public Object getPriceArea() {
        Map<String, Object> map = new HashMap<>();
        Double minPrice = new Double(0);
        Double maxPrice = new Double(0);
        if (null != productService.findFirstByStateOrderByPriceAsc(new Integer(2))) {
            minPrice = productService.findFirstByStateOrderByPriceAsc(new Integer(2)).getPrice();
        }
        if (null != productService.findFirstByStateOrderByPriceDesc(new Integer(2))) {
            maxPrice = productService.findFirstByStateOrderByPriceDesc(new Integer(2)).getPrice();
        }
        map.put("minPrice", minPrice);
        map.put("maxPrice", maxPrice);
        return ResultUtil.success(map);
    }

    @PostMapping(value = "/front/getProductListByShop")
    public Object getProductListByShop(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Shop shop = shopService.findShopById(id);
        if (null == shop) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(shop.getUsingProducts());
    }
}
