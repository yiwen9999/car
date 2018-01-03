package com.hex.car.controller;

import com.hex.car.domain.*;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.*;
import com.hex.car.utils.FileUtil;
import com.hex.car.utils.HexUtil;
import com.hex.car.utils.Md5SaltTool;
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
import javax.transaction.Transactional;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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

    @Autowired
    private ProductService productService;

    @Autowired
    private ImgShopService imgShopService;

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.zip-file-limit}")
    private Long zipFileLimit;

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
    @Transactional
    @PostMapping(value = "/saveShop")
    public Object saveShop(Shop shop,
                           String placeId,
                           String username,
                           String password,
                           @RequestParam(value = "mainFile", required = false) MultipartFile mainFile,
                           @RequestParam(value = "files", required = false) List<MultipartFile> files) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (!HexUtil.validateString(placeId)) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "所在地区" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(shop.getName())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "4S店名称" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(username)) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "4S店账号" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(password)) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "4S店密码" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(shop.getAddress())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "地址" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateDouble(shop.getLatitude())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "纬度" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateDouble(shop.getLongitude())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "经度" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (!HexUtil.validateString(shop.getRemark())) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "4S店描述" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (null != userService.findUserByUsername(username)) {
            return ResultUtil.error(ResultEnum.ERROR_USERNAME.getCode(), ResultEnum.ERROR_USERNAME.getMsg());
        }
        Place place = placeService.findPlaceById(placeId);
        shop.setPlace(place);
        if (null == mainFile) {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "主图" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        MultipartFile file;
        ImgShop imgShop;
        List<ImgShop> imgShopList = new ArrayList<>();
        String fileName = mainFile.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        try {
            FileUtil.uploadImgFile(mainFile, path, fileName, zipFileLimit);
            imgShop = new ImgShop();
            imgShop.setFileName(fileName);
            imgShop.setMain(new Boolean(true));
            imgShopList.add(imgShop);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
        }
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            fileName = file.getOriginalFilename();
            suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            try {
                FileUtil.uploadImgFile(file, path, fileName, zipFileLimit);
                imgShop = new ImgShop();
                imgShop.setFileName(fileName);
                imgShop.setMain(new Boolean(false));
                imgShopList.add(imgShop);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(Md5SaltTool.getEncryptedPwd(password));
        user.setImgUser(new ImgUser());
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
        if (null != place) {
            saveShop.setPlace(place);
        } else {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "所在地区" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (null != shop.getName() && !shop.getName().equals("")) {
            saveShop.setName(shop.getName());
        } else {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "4S店名称" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (null != shop.getLinkman() && !shop.getLinkman().equals(""))
            saveShop.setLinkman(shop.getLinkman());
        if (null != shop.getAddress() && !shop.getAddress().equals("")) {
            saveShop.setAddress(shop.getAddress());
        } else {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "地址" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (null != shop.getCustomService() && !shop.getCustomService().equals(""))
            saveShop.setCustomService(shop.getCustomService());
        if (null != shop.getPhone() && !shop.getPhone().equals(""))
            saveShop.setPhone(shop.getPhone());
        if (null != shop.getEmail() && !shop.getEmail().equals(""))
            saveShop.setEmail(shop.getEmail());
        if (null != shop.getRemark() && !shop.getRemark().equals("")) {
            saveShop.setRemark(shop.getRemark());
        } else {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "4S店描述" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (null != shop.getLongitude()) {
            saveShop.setLongitude(shop.getLongitude());
        } else {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "经度" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }
        if (null != shop.getLatitude()) {
            saveShop.setLatitude(shop.getLatitude());
        } else {
            return ResultUtil.error(ResultEnum.ERROR_NULLPARAM.getCode(), "纬度" + ResultEnum.ERROR_NULLPARAM.getMsg());
        }

        return ResultUtil.success(shopService.saveShop(saveShop));
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
     * 根据名称模糊搜索在用4s店集合，按名称排序返回10个（用于快速检索）
     *
     * @param name 4s店名称
     * @return
     */
    @PostMapping(value = "/searchTop10UsingShopListByName")
    public Object searchTop10UsingShopListByName(String name) {
        if (name == null || name.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(shopService.findTop10ShopsByNameLikeAndStateOrderByName(name, new Integer(2)));
    }

    @PostMapping(value = "/searchShopList")
    public Object searchShopList(String beginTime, String endTime, String name,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "1000") Integer size,
                                 @RequestParam(defaultValue = "createTime") String sortStr,
                                 @RequestParam(defaultValue = "desc") String asc) {
        Sort sort;
        if (asc.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, sortStr);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortStr);
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Map<String, Object> condition = new HashMap<>();
        condition.put("name", name);
        condition.put("minCreateTime", HexUtil.formatBeginTimeString(beginTime));
        condition.put("maxCreateTime", HexUtil.formatEndTimeString(endTime));
        return ResultUtil.success(shopService.findShops(condition, pageRequest));
    }

    /**
     * 根据身份获取在用4s店，商品集合
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/getShopAndProductListByIdentity")
    public Object getShopAndProductListByIdentity(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        List<Shop> shops = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        User user = HexUtil.getUser(request);
        if (null != user) {
            if ("root".equals(user.getId())) {
                shops.addAll(shopService.findShopsByStateOrderByName(new Integer(2)));
                products.addAll(productService.findProductsByStateOrderByName(new Integer(2)));
            } else if (null != user.getShop()) {
                shops.add(user.getShop());
                products.addAll(user.getShop().getUsingProducts());
            }
        }
        map.put("shopList", shops);
        map.put("productList", products);
        return ResultUtil.success(map);
    }

    @GetMapping(value = "/getShopListByIdentity")
    public Object getShopListByIdentity(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        List<Shop> shops = new ArrayList<>();
        User user = HexUtil.getUser(request);
        if (null != user) {
            if ("root".equals(user.getId())) {
                shops.addAll(shopService.findShopsByStateOrderByName(new Integer(2)));
            } else if (null != user.getShop()) {
                shops.add(shopService.findShopById(user.getShop().getId()));
            } else { // 文章编辑账号，可对4s店所售车辆进行文章编辑
                shops.addAll(shopService.findShopsByStateOrderByName(new Integer(2)));
            }
        }
        map.put("shopList", shops);
        return ResultUtil.success(map);
    }

    @GetMapping(value = "/getProductListByShopId")
    public Object getProductListByShopId(String id, HttpServletRequest request) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Shop shop = shopService.findShopById(id);
        if (null == shop) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(shop.getUsingProducts());
    }

    /**
     * 编辑4s店图片
     *
     * @param id
     * @param mainFile
     * @param files
     * @return
     */
    @PostMapping(value = "/editImgShop")
    public Object editImgShop(String id,
                              @RequestParam(value = "mainFile", required = false) MultipartFile mainFile,
                              @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        if (null == id || "".equals(id)) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Shop shop = shopService.findShopById(id);
        if (null == shop) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        String fileName;
        String suffixName;
        ImgShop imgShop;
        ImgShop oldImgShop;
        List<ImgShop> imgShopList = new ArrayList<>();
        if (null != mainFile) {
            fileName = mainFile.getOriginalFilename();
            suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            try {
                if (null != shop.getMainImgShops() && !shop.getMainImgShops().isEmpty()) {
                    oldImgShop = shop.getMainImgShops().iterator().next();
                    File deleteFile = new File(path + oldImgShop.getFileName());
                    if (deleteFile.exists()) {
                        deleteFile.delete();
                    }
                    shop.getMainImgShops().clear();
                    imgShopService.deleteImgShop(oldImgShop);
                }
                FileUtil.uploadImgFile(mainFile, path, fileName, zipFileLimit);
                imgShop = new ImgShop();
                imgShop.setFileName(fileName);
                imgShop.setMain(new Boolean(true));
                imgShopList.add(imgShop);
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
                imgShop = new ImgShop();
                imgShop.setFileName(fileName);
                imgShop.setMain(new Boolean(false));
                imgShopList.add(imgShop);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
            }
        }
        shop.getImgShops().addAll(imgShopList);
        return ResultUtil.success(shopService.saveShop(shop));
    }

    /**
     * 删除4s店图片
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteImgShop")
    public Object deleteImgShop(String id) {
        if (null == id || "".equals(id)) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        ImgShop imgShop = imgShopService.findImgShopById(id);
        if (null == imgShop) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        File deleteFile = new File(path + imgShop.getFileName());
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
        imgShopService.deleteImgShop(imgShop);
        return ResultUtil.success();
    }

}
