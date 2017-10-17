package com.hex.car.controller;

import com.hex.car.domain.Advertising;
import com.hex.car.domain.ImgAD;
import com.hex.car.domain.ImgShop;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.AdvertisingService;
import com.hex.car.utils.FileUtil;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * 广告相关controller
 * User: hexuan
 * Date: 2017/9/29
 * Time: 下午3:00
 */
@RestController
public class AdvertisingController {

    @Autowired
    private AdvertisingService advertisingService;

    @Value("${web.upload-path}")
    private String path;

    /**
     * 广告保存
     *
     * @param advertising 广告
     * @return
     */
    @PostMapping(value = "/saveAdvertising")
    public Object saveAdvertising(Advertising advertising,
                                  @RequestParam(value = "imgAdFile", required = false) MultipartFile imgAdFile) {
        ImgAD imgAd = new ImgAD();
        MultipartFile file = imgAdFile;
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        try {
            FileUtil.uploadFile(file.getBytes(), path, fileName);
            imgAd.setFileName(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.UPLOAD_FAIL.getCode(), ResultEnum.UPLOAD_FAIL.getMsg());
        }
        advertising.setImgAD(imgAd);
        return ResultUtil.success(advertisingService.saveAdvertising(advertising));
    }

    /**
     * 获取全部广告集合
     *
     * @return
     */
    @GetMapping(value = "/getAllAdvertisingList")
    public Object getAllAdvertisingList() {
        return ResultUtil.success(advertisingService.findAllAdvertisingList());
    }
}
