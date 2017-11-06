package com.hex.car.controller;

import com.hex.car.domain.ImgShop;
import com.hex.car.domain.Place;
import com.hex.car.domain.Shop;
import com.hex.car.domain.User;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.PlaceService;
import com.hex.car.service.ShopService;
import com.hex.car.service.UserService;
import com.hex.car.utils.FileUtil;
import com.hex.car.utils.Md5SaltTool;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 4s店相关controller
 * User: hexuan
 * Date: 2017/10/13
 * Time: 下午2:26
 */
@RestController
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private UserService userService;

    @Value("${web.upload-path}")
    private String path;

    /**
     * 添加4s店
     *
     * @param shop     4s店
     * @param placeId  地点id
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/saveShop")
    public Object saveShop(Shop shop,
                           String placeId,
                           String username,
                           String password,
                           @RequestParam(value = "files", required = false) List<MultipartFile> files) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Place place = placeService.findPlaceById(placeId);
        shop.setPlace(place);
        MultipartFile file;
        ImgShop imgShop;
        List<ImgShop> imgShopList = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            try {
                FileUtil.uploadFile(file.getBytes(), path, fileName);
                imgShop = new ImgShop();
                imgShop.setFileName(fileName);
                imgShopList.add(imgShop);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(Md5SaltTool.getEncryptedPwd(password));
        userService.saveUser(user);
        shop.setUser(user);
        shop.setImgShops(imgShopList);
        return ResultUtil.success(shopService.saveShop(shop));
    }

    /**
     * 更新4s店
     *
     * @param shop    4s店
     * @param placeId 地点id
     * @return
     */
    @PostMapping(value = "/updateShop")
    public Object updateShop(Shop shop, String placeId) {
        Place place = placeService.findPlaceById(placeId);
        Shop saveShop = shopService.findShopById(shop.getId());
        if (null != place)
            saveShop.setPlace(place);
        if (null != shop.getLongitude())
            saveShop.setLongitude(shop.getLongitude());
        if (null != shop.getLatitude())
            saveShop.setLatitude(shop.getLatitude());
        if (null != shop.getPhone() && !shop.getPhone().equals(""))
            saveShop.setPhone(shop.getPhone());
        if (null != shop.getAddress() && !shop.getAddress().equals(""))
            saveShop.setAddress(shop.getAddress());
        if (null != shop.getName() && !shop.getName().equals(""))
            saveShop.setName(shop.getName());
        return ResultUtil.success(saveShop);
    }

    /**
     * 停启用4s店
     *
     * @param id 4s店id
     * @return
     */
    @PostMapping(value = "/updateShopState")
    public Object updateShopState(String id) {
        Shop shop = shopService.findShopById(id);
        if (null == shop) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (2 == shop.getState()) {
            shop.setState(new Integer(-1));
        } else {
            shop.setState(new Integer(2));
        }
        return ResultUtil.success(shopService.saveShop(shop));
    }

    /**
     * 获取4s店信息
     *
     * @param id 4s店id
     * @return
     */
    @PostMapping(value = "/getShopInfo")
    public Object getShopInfo(String id) {
        return ResultUtil.success(shopService.findShopById(id));
    }

    /**
     * 获取全部4s店集合
     *
     * @return
     */
    @GetMapping(value = "/getAllShopList")
    public Object getAllShopList() {
        return ResultUtil.success(shopService.findAllShop());
    }

    /**
     * 删除4s店
     *
     * @param id 4s店id
     * @return
     */
    @PostMapping(value = "/deleteShop")
    public Object deleteShop(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Shop shop = shopService.findShopById(id);
        if (null == shop) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        File deleteFile;
        for (ImgShop imgShop : shop.getImgShops()) {
            deleteFile = new File(path + imgShop.getFileName());
            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }
        shopService.deleteShop(shop);
        return ResultUtil.success();
    }

    /**
     * 获取4个最新4s店集合（前台页面用）
     *
     * @return
     */
    @GetMapping("/front/get4ShopList")
    public Object get4ProductList() {
        List<Shop> shops = shopService.findTop4ByStateOrderByCreateTimeDesc(new Integer(2));
//        List<ImgShop> imgShops = new ArrayList<>();
//        ImgShop imgShop;
//        Map<String, Object> map = new HashMap<>();
//        for (int i = 0; i < shops.size(); i++) {
//            imgShop = new ImgShop();
//            for (int j = 0; j < shops.get(i).getImgShops().size(); j++) {
//                if (shops.get(i).getImgShops().get(j).getMain()) {
//                    imgShop = shops.get(i).getImgShops().get(j);
//                }
//            }
//            imgShops.add(imgShop);
//        }
//        map.put("shops", shops);
//        map.put("imgs", imgShops);
        return ResultUtil.success(shops);
    }
}
