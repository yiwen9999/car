package com.hex.car.controller;

import com.hex.car.service.CarService;
import com.hex.car.service.ImgProductService;
import com.hex.car.service.ShopService;
import com.hex.car.utils.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 上午11:43
 */
@Api(value = "TestController", tags = {"测试相关接口"})
@RestController
public class TestController {

    @Autowired
    private ImgProductService ImgProductService;

    @Autowired
    private CarService carService;

    @Autowired
    private ShopService shopService;

    @Value("${web.upload-path}")
    private String path;

    public Object testSms(){

        return ResultUtil.success();
    }

//    /**
//     * 保存4s店
//     *
//     * @param shop
//     * @return
//     */
//    @PostMapping(value = "/saveShop")
//    public Object saveShop(Shop shop) {
//        return ResultUtil.success(shopService.saveShop(shop));
//    }
//
//    /**
//     * 保存车辆
//     *
//     * @param car
//     * @param shopId
//     * @param files
//     * @return
//     */
//    @PostMapping(value = "/saveCar")
//    public Object saveCar(Car car, String shopId, @RequestParam("files") List<MultipartFile> files) {
//        Shop shop = shopService.findShopById(shopId);
//        car.setShop(shop);
//
//        MultipartFile file;
//        List<ImgProduct> imgProducts = new ArrayList<>();
//        for (int i = 0; i < files.size(); i++) {
//            file = files.get(i);
//            String fileName = file.getOriginalFilename();
//            String suffixName = fileName.substring(fileName.lastIndexOf("."));
//            fileName = UUID.randomUUID() + suffixName;
//            try {
//                FileUtil.uploadFile(file.getBytes(), path, fileName);
//                ImgProduct imgProduct = new ImgProduct();
//                imgProduct.setFileName(fileName);
//                imgProducts.add(imgProduct);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
//            }
//        }
//
//        car.setImgCars(imgProducts);
//
//        return ResultUtil.success(carService.saveCar(car));
//    }
//
//    @PostMapping(value = "/getCarInfo")
//    public Object getCarInfo(@Valid String carId) {
//        return ResultUtil.success(carService.findCarById(carId));
//    }
}
