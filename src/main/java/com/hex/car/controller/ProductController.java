package com.hex.car.controller;

import com.hex.car.domain.ImgProduct;
import com.hex.car.domain.Product;
import com.hex.car.domain.Shop;
import com.hex.car.domain.User;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.CarService;
import com.hex.car.service.ProductService;
import com.hex.car.service.ShopService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Value("${web.upload-path}")
    private String path;

    /**
     * 根据名称查询启用商品集合
     *
     * @param name 商品名称
     * @return
     */
    @PostMapping(value = "/searchUsingProductListByName")
    public Object searchUsingProductListByName(String name) {
        if (null == name) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(productService.findProductsByNameLikeAndStateOrderByName(name, new Integer(2)));
    }

    /**
     * 根据商品名称，登录人身份，查询启用商品集合（暂未用）
     *
     * @param name    商品名称
     * @param request request获取登录账号
     * @return
     */
    @PostMapping(value = "/searchUsingProductListByNameAndIdentity")
    public Object searchUsingProductListByNameAndIdentity(String name, HttpServletRequest request) {
        Object object = request.getSession().getAttribute("user");
        if (null == object) {
            return ResultUtil.error(ResultEnum.UN_LOGIN.getCode(), ResultEnum.UN_LOGIN.getMsg());
        }
        User user = (User) object;
        if (null == name) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (user.getId().equals("root")) {
            return ResultUtil.success(productService.findProductsByNameLikeAndStateOrderByName(name, new Integer(2)));
        } else if (null != user.getShop()) {
            return ResultUtil.success(productService.findProductsByNameLikeAndStateAndShopOrderByName(name, new Integer(2), user.getShop()));
        } else {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
    }

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
        if (carId == null || carId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        User user = HexUtil.getUser(request);
        if (null == user) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Shop shop;
        if ("root".equals(user.getId())) {
            if (null == shopId || "".equals(shopId)) {
                return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
            }
            shop = shopService.findShopById(shopId);
        } else {
            shop = user.getShop();
        }
        if (null == shop) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        product.setCar(carService.findCarById(carId));
        product.setShop(shop);
        if (null == mainFile) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        String fileName = mainFile.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        ImgProduct imgProduct;
        List<ImgProduct> imgProductList = new ArrayList<>();
        try {
            FileUtil.uploadFile(mainFile.getBytes(), path, fileName);
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
                FileUtil.uploadFile(file.getBytes(), path, fileName);
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
     * 根据名称，创建时间查询商品集合
     *
     * @param beginTime
     * @param endTime
     * @param name
     * @return
     */
    @PostMapping(value = "/searchProductList")
    public Object searchProductList(String beginTime, String endTime, String name) {
        return ResultUtil.success(productService.findProductListByCreateTimeAndNameAndIdentity(HexUtil.formatBeginTimeString(beginTime), HexUtil.formatEndTimeString(endTime), name, null));
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
    public Object searchProductList(String beginTime, String endTime, String name, HttpServletRequest request) {
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

}
