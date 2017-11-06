package com.hex.car.controller;

import com.hex.car.domain.ImgProduct;
import com.hex.car.domain.Product;
import com.hex.car.domain.User;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.CarService;
import com.hex.car.service.ProductService;
import com.hex.car.service.ShopService;
import com.hex.car.utils.FileUtil;
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
     * 根据商品名称，登录人身份，查询启用商品集合
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
     * @param shopId  4s店id
     * @param files   对应图片文件集合
     * @return
     */
    @PostMapping(value = "/saveProduct")
    public Object saveProduct(Product product,
                              String carId,
                              String shopId,
                              @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        if (carId == null || carId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (shopId == null || shopId.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        product.setCar(carService.findCarById(carId));
        product.setShop(shopService.findShopById(shopId));
        MultipartFile file;
        ImgProduct imgProduct;
        List<ImgProduct> imgProductList = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            try {
                FileUtil.uploadFile(file.getBytes(), path, fileName);
                imgProduct = new ImgProduct();
                imgProduct.setFileName(fileName);
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
     * 获取4个最新商品集合（前台页面用）
     *
     * @return
     */
    @GetMapping("/front/get4ProductList")
    public Object get4ProductList() {
        List<Product> products = productService.findTop4ByStateOrderByCreateTimeDesc(new Integer(2));
//        List<ImgProduct> imgProducts = new ArrayList<>();
//        ImgProduct imgProduct;
//        Map<String, Object> map = new HashMap<>();
//        for (int i = 0; i < products.size(); i++) {
//            imgProduct = new ImgProduct();
//            for (int j = 0; j < products.get(i).getImgProducts().size(); j++) {
//                if (products.get(i).getImgProducts().get(j).getMain()) {
//                    imgProduct = products.get(i).getImgProducts().get(j);
//                }
//            }
//            imgProducts.add(imgProduct);
//        }
//        map.put("products", products);
//        map.put("imgs", imgProducts);
        return ResultUtil.success(products);
    }
}
