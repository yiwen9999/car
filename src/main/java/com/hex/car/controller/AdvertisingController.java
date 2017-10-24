package com.hex.car.controller;

import com.hex.car.domain.Advertising;
import com.hex.car.domain.ImgAD;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.AdvertisingService;
import com.hex.car.service.ImgADService;
import com.hex.car.utils.FileUtil;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Autowired
    private ImgADService imgADService;

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
                                  @RequestParam(value = "file", required = false) MultipartFile file) {
        ImgAD imgAd = new ImgAD();
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
     * 广告修改
     *
     * @param advertising 广告
     * @param file        广告图片
     * @return
     */
    @PostMapping(value = "/updateAdvertising")
    public Object updateAdvertising(Advertising advertising,
                                    @RequestParam(value = "file", required = false) MultipartFile file) {
        if (null == advertising.getId() || advertising.getId().equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Advertising saveAdvertising = advertisingService.findAdvertisingById(advertising.getId());
        if (null == saveAdvertising) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (null != file) {
            ImgAD imgAd = new ImgAD();
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
            File deleteFile = new File(path + saveAdvertising.getImgAD().getFileName());
            deleteFile.delete();
            imgADService.deleteImgAD(saveAdvertising.getImgAD());
            saveAdvertising.setImgAD(imgAd);
        }
        if (null != advertising.getName() && !advertising.getName().equals("")) {
            saveAdvertising.setName(advertising.getName());
        }
        if (null != advertising.getSort()) {
            saveAdvertising.setSort(advertising.getSort());
        }
        if (null != advertising.getUrl() && !advertising.getUrl().equals("")) {
            saveAdvertising.setUrl(advertising.getUrl());
        }
        return ResultUtil.success(advertisingService.saveAdvertising(saveAdvertising));
    }

    /**
     * 停启用广告
     *
     * @return
     */
    @PostMapping(value = "/updateAdvertisingState")
    public Object updateAdvertisingState(String advertisingId) {
        Advertising advertising = advertisingService.findAdvertisingById(advertisingId);
        if (null == advertising) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (2 == advertising.getState()) {
            advertising.setState(new Integer(-1));
        } else {
            advertising.setState(new Integer(2));
        }
        return ResultUtil.success(advertisingService.saveAdvertising(advertising));
    }

    /**
     * 获取广告信息
     *
     * @param advertisingId 广告id
     * @return
     */
    @PostMapping(value = "/getAdvertisingInfo")
    public Object getAdvertisingInfo(String advertisingId) {
        return ResultUtil.success(advertisingService.findAdvertisingById(advertisingId));
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

    /**
     * 获取在用广告集合
     *
     * @return
     */
    @GetMapping(value = "/getUsingAdvertisingList")
    public Object getUsingAdvertisingList() {
        return ResultUtil.success(advertisingService.findAdvertisingsByStateOrderBySort(new Integer(2)));
    }
}
