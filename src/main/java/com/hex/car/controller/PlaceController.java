package com.hex.car.controller;

import com.hex.car.domain.Place;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.PlaceService;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地点相关controller
 * User: hexuan
 * Date: 2017/9/27
 * Time: 下午4:40
 */
@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    /**
     * 根据父地点id获取子地点集合
     *
     * @param parentId 父地点id
     * @return
     */
    @PostMapping(value = "/getPlaceListByParentId")
    public Object getPlaceListByParentId(String parentId) {
        Place parentPlace = placeService.findPlaceById(parentId);
        if (null == parentPlace) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(parentPlace.getChilds());
    }

    /**
     * 根据父地点id获取在用子地点集合
     *
     * @param parentId 父地点id
     * @return
     */
    @PostMapping(value = "/getUsingPlaceListByParentId")
    public Object getUsingPlaceListByParentId(String parentId) {
        Place parentPlace = placeService.findPlaceById(parentId);
        if (null == parentPlace) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        return ResultUtil.success(parentPlace.getUsingChilds());
    }

    /**
     * 获取北京及其子地点
     *
     * @return
     */
    @GetMapping(value = "/getBJPlaceList")
    public Object getBJPlaceList() {
        Place bjPlace = placeService.findPlaceById("110100");
        if (null == bjPlace) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Map<String, Object> map = new HashMap<>();
        List<Place> list = new ArrayList<>();
        list.add(bjPlace);
        map.put("topPlaceList", list);
        map.put("childPlaceList", bjPlace.getUsingChilds());
        return ResultUtil.success(map);
    }

    /**
     * 获取地点库1，2级地点集合
     *
     * @return
     */
    @GetMapping(value = "/getPlaceListByLevelOneTwo")
    public Object getPlaceListByLevelOneTwo() {
        Map<String, Object> map = new HashMap<>();
        map.put("topPlaceList", placeService.findPlacesByLevelOrderById(new Integer(1)));
        map.put("childPlaceList", placeService.findPlacesByLevelOrderById(new Integer(2)));
        return ResultUtil.success(map);
    }


    @GetMapping(value = "/getPlaceListByPage")
    public Object getPlaceListByPage(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Page placePage = placeService.findAllPlaceListByPage(page, size, sort);
        return ResultUtil.success(placePage);
    }

}
