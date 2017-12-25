package com.hex.car.controller;

import com.hex.car.domain.*;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.CarService;
import com.hex.car.service.ImgProductService;
import com.hex.car.service.ProductService;
import com.hex.car.service.ShopService;
import com.hex.car.utils.FileUtil;
import com.hex.car.utils.HexUtil;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * User: hexuan
 * Date: 2017/10/25
 * Time: 下午2:52
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CarService carService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ImgProductService imgProductService;

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.zip-file-limit}")
    private Long zipFileLimit;

    /**
     * 保存商品
     *
     * @param product 商品
     * @param carId   车辆id
     * @param files   对应图片文件集合
     * @return
     */
    @PostMapping(value = "/saveProduct")
    public Object saveProduct(Product product,
                              String carId,
                              String shopId,
                              HttpServletRequest request,
                              @RequestParam(value = "mainFile", required = false) MultipartFile mainFile,
                              @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        if (!HexUtil.validateDouble(product.getPrice())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "售价" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(product.getDetails())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "详情说明" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (carId == null || carId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "车型库选择" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Shop shop;
        if ("root".equals(user.getId())) {
            if (null == shopId || "".equals(shopId)) {
                return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "4S店" + ResultEnum.ERROR_NULLPARAM.getMsg());
            }
            shop = shopService.findShopById(shopId);
        } else {
            shop = user.getShop();
        }
        if (null == shop) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "4S店" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        product.setCar(carService.findCarById(carId));
        product.setShop(shop);
        if (null == mainFile) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "主图" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        String fileName = mainFile.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        ImgProduct imgProduct;
        List<ImgProduct> imgProductList = new ArrayList<>();
        try {
            FileUtil.uploadImgFile(mainFile, path, fileName, zipFileLimit);
            imgProduct = new ImgProduct();
            imgProduct.setFileName(fileName);
            imgProduct.setMain(new Boolean(true));
            imgProductList.add(imgProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
        }
        MultipartFile file;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            fileName = file.getOriginalFilename();
            suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            try {
                FileUtil.uploadImgFile(file, path, fileName, zipFileLimit);
                imgProduct = new ImgProduct();
                imgProduct.setFileName(fileName);
                imgProduct.setMain(new Boolean(false));
                imgProductList.add(imgProduct);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        }
        product.getImgProducts().addAll(imgProductList);
        return ResultUtil.success(productService.saveProduct(product));
    }

    /**
     * 修改所售车辆
     *
     * @param product
     * @param carId
     * @param shopId
     * @param request
     * @return
     */
    @PostMapping(value = "/updateProduct")
    public Object updateProduct(Product product,
                                String carId,
                                String shopId,
                                HttpServletRequest request) {
        if (!HexUtil.validateDouble(product.getPrice())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "售价" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(product.getDetails())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "详情说明" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (carId == null || carId.equals("") || null == product.getId() || "".equals(product.getId())) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Shop shop;
        Car car = carService.findCarById(carId);
        Product saveProduct = productService.findProductById(product.getId());
        if ("root".equals(user.getId())) {
            if (null == shopId || "".equals(shopId)) {
                return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
            }
            shop = shopService.findShopById(shopId);
        } else {
            shop = user.getShop();
        }
        if (null == shop || null == saveProduct || null == car) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        saveProduct.setCar(car);
        saveProduct.setShop(shop);
        saveProduct.setName(product.getName());
        saveProduct.setPrice(product.getPrice());
        saveProduct.setDetails(product.getDetails());
        return ResultUtil.success(productService.saveProduct(saveProduct));
    }

    /**
     * 停启用商品
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/updateProductState")
    public Object updateProductState(String id) {
        if (id == null || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Product product = productService.findProductById(id);
        if (null == product) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (product.getState() == 2) {
            product.setState(new Integer(-1));
        } else {
            product.setState(new Integer(2));
        }
        return ResultUtil.success(productService.saveProduct(product));
    }

    /**
     * 删除商品
     *
     * @param id 商品id
     * @return
     */
    @PostMapping(value = "/deleteProduct")
    public Object deleteProduct(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Product product = productService.findProductById(id);
        if (null == product) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        File deleteFile;
        for (ImgProduct imgProduct : product.getImgProducts()) {
            deleteFile = new File(path + imgProduct.getFileName());
            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }
        productService.deleteProduct(product);
        return ResultUtil.success();
    }

    /**
     * 根据名称，创建时间，创建人查询商品集合
     *
     * @param beginTime
     * @param endTime
     * @param name
     * @param request
     * @return
     */
    @PostMapping(value = "/searchProductListByIdentity")
    public Object searchProductListByIdentity(String beginTime, String endTime, String name, HttpServletRequest request) {
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (user.getId().equals("root")) {
            return ResultUtil.success(productService.findProductListByCreateTimeAndNameAndIdentity(HexUtil.formatBeginTimeString(beginTime), HexUtil.formatEndTimeString(endTime), name, null));
        } else if (null != user.getShop()) {
            return ResultUtil.success(productService.findProductListByCreateTimeAndNameAndIdentity(HexUtil.formatBeginTimeString(beginTime), HexUtil.formatEndTimeString(endTime), name, user.getShop()));
        } else {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
    }

    @PostMapping(value = "/searchProductList")
    public Object searchProductList(String beginTime, String endTime, String name, HttpServletRequest request,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "1000") Integer size,
                                    @RequestParam(defaultValue = "createTime") String sortStr,
                                    @RequestParam(defaultValue = "desc") String asc) {
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.ERROR_IDENTITY.getCode(), ResultEnum.ERROR_IDENTITY.getMsg());
        }
        Sort sort;
        if (asc.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, sortStr);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortStr);
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Map<String, Object> condition = new HashMap<>();
        if (null != user.getShop()) {
            condition.put("shopId", user.getShop().getId());
        }
        condition.put("minCreateTime", HexUtil.formatBeginTimeString(beginTime));
        condition.put("maxCreateTime", HexUtil.formatEndTimeString(endTime));
        condition.put("name", name);
        return ResultUtil.success(productService.findProducts(condition, pageRequest));
    }

    @GetMapping(value = "getAllProductList")
    public Object getAllProductList() {
        return ResultUtil.success(productService.findAllProduct());
    }

    /**
     * 根据身份，名称模糊搜索在用商品集合，按名称排序返回10个（用于快速检索）
     *
     * @param name    商品名称
     * @param request request获取当前操作人员身份
     * @return
     */
    @PostMapping(value = "/searchTop10UsingProductListByNameAndIdentity")
    public Object searchTop10UsingProductListByNameAndIdentity(String name, String shopId, HttpServletRequest request) {
        if (name == null || name.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (user.getId().equals("root")) {
            /**
             * 管理员身份操作时，判断是否有4s店id限制。
             * 有shopId查询该4s店内商品。
             * 没有shopId的，查询所有商品。
             */
            Shop shop;
            if (null != shopId && !shopId.equals("")) {
                shop = shopService.findShopById(shopId);
                if (null == shop) {
                    return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
                }
                return ResultUtil.success(productService.findTop10ProductsByNameLikeAndStateAndShopOrderByName(name, new Integer(2), shop));
            } else {
                return ResultUtil.success(productService.findTop10ProductsByNameLikeAndStateOrderByName(name, new Integer(2)));
            }

        } else if (null != user.getShop()) {
            /**
             * 4s店身份操作，查询该4s店内商品
             */
            return ResultUtil.success(productService.findTop10ProductsByNameLikeAndStateAndShopOrderByName(name, new Integer(2), user.getShop()));
        } else {
            return ResultUtil.error(ResultEnum.ERROR_IDENTITY.getCode(), ResultEnum.ERROR_IDENTITY.getMsg());
        }
    }

    /**
     * 在售车辆图片编辑
     *
     * @param id
     * @param mainFile
     * @param files
     * @return
     */
    @PostMapping(value = "/editImgProduct")
    public Object editImgProduct(String id,
                                 @RequestParam(value = "mainFile", required = false) MultipartFile mainFile,
                                 @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        if (null == id || "".equals(id)) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Product product = productService.findProductById(id);
        if (null == product) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        String fileName;
        String suffixName;
        ImgProduct imgProduct;
        ImgProduct oldImgProduct;
        List<ImgProduct> imgProductList = new ArrayList<>();
        if (null != mainFile) {
            fileName = mainFile.getOriginalFilename();
            suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            try {
                if (null != product.getMainImgProducts() && !product.getMainImgProducts().isEmpty()) {
                    oldImgProduct = product.getMainImgProducts().iterator().next();
                    File deleteFile = new File(path + oldImgProduct.getFileName());
                    if (deleteFile.exists()) {
                        deleteFile.delete();
                    }
                    product.getMainImgProducts().clear();
                    imgProductService.deleteImgProduct(oldImgProduct);
                }
                FileUtil.uploadImgFile(mainFile, path, fileName, zipFileLimit);
                imgProduct = new ImgProduct();
                imgProduct.setFileName(fileName);
                imgProduct.setMain(new Boolean(true));
                imgProductList.add(imgProduct);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        }

        MultipartFile file;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            fileName = file.getOriginalFilename();
            suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            try {
                FileUtil.uploadImgFile(file, path, fileName, zipFileLimit);
                imgProduct = new ImgProduct();
                imgProduct.setFileName(fileName);
                imgProduct.setMain(new Boolean(false));
                imgProductList.add(imgProduct);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        }
        product.getImgProducts().addAll(imgProductList);
        return ResultUtil.success(productService.saveProduct(product));
    }

    @PostMapping(value = "/deleteImgProduct")
    public Object deleteImgProduct(String id) {
        if (null == id || "".equals(id)) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        ImgProduct imgProduct = imgProductService.findImgProductById(id);
        if (null == imgProduct) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        File deleteFile = new File(path + imgProduct.getFileName());
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
        imgProductService.deleteImgProduct(imgProduct);
        return ResultUtil.success();
    }

}
