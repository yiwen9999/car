package com.hex.car.controller;

import com.hex.car.domain.User;
import com.hex.car.utils.HexUtil;
import com.hex.car.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础controller
 * User: hexuan
 * Date: 2017/9/23
 * Time: 下午4:06
 */
@RestController
public class BaseController {
    @GetMapping(value = "/getChannelList")
    public Object getChannelList(HttpServletRequest request) {
        User user = HexUtil.getUser(request);
        return ResultUtil.success(getChannelMapByUser(user));
    }

    private List<Map> getChannelMapByUser(User user) {
        List<Map> mapList = new ArrayList<>();
        if (null != user) {
            if (user.getId().equals("root")) {
                List<Map> childMapList = new ArrayList<>();
                Map<String, Object> map;
                Map<String, String> childMap = new HashMap<>();
                map = new HashMap<>();
                map.put("id", "c1");
                map.put("name", "会员管理");
                map.put("url", "/");
                childMap.put("name", "会员列表");
                childMap.put("url", "/toPersonnelList");
                childMapList.add(childMap);
                map.put("childList", childMapList);
                mapList.add(map);

                map = new HashMap<>();
                map.put("id", "c2");
                map.put("name", "品牌管理");
                map.put("url", "/");
                childMapList = new ArrayList<>();
                childMap = new HashMap<>();
                childMap.put("name", "品牌型号列表");
                childMap.put("url", "/toBrandList");
                childMapList.add(childMap);
                map.put("childList", childMapList);
                mapList.add(map);

                map = new HashMap<>();
                map.put("id", "c3");
                map.put("name", "车辆管理");
                map.put("url", "/");
                childMapList = new ArrayList<>();
                childMap = new HashMap<>();
                childMap.put("name", "车辆列表");
                childMap.put("url", "/toProductList");
                childMapList.add(childMap);
                map.put("childList", childMapList);
                mapList.add(map);

                map = new HashMap<>();
                map.put("id", "c4");
                map.put("name", "4s店管理");
                map.put("url", "/");
                childMapList = new ArrayList<>();
                childMap = new HashMap<>();
                childMap.put("name", "4s店列表");
                childMap.put("url", "/toShopList");
                childMapList.add(childMap);
                map.put("childList", childMapList);
                mapList.add(map);

                map = new HashMap<>();
                map.put("id", "c5");
                map.put("name", "广告管理");
                map.put("url", "/");
                childMapList = new ArrayList<>();
                childMap = new HashMap<>();
                childMap.put("name", "广告列表");
                childMap.put("url", "/toAdvertisingList");
                childMapList.add(childMap);
                map.put("childList", childMapList);
                mapList.add(map);

                map = new HashMap<>();
                map.put("id", "c6");
                map.put("name", "文章管理");
                map.put("url", "/");
                childMapList = new ArrayList<>();
                childMap = new HashMap<>();
                childMap.put("name", "文章列表");
                childMap.put("url", "/toEvaluateList");
                childMapList.add(childMap);
                map.put("childList", childMapList);
                mapList.add(map);

                return mapList;
            } else {
                List<Map> childMapList = new ArrayList<>();
                Map<String, Object> map;
                Map<String, String> childMap = new HashMap<>();
                map = new HashMap<>();
                map.put("id", "c1");
                map.put("name", "所售车辆管理");
                map.put("url", "/");
                childMap.put("name", "所售车辆列表");
                childMap.put("url", "/toProductList");
                childMapList.add(childMap);
                map.put("childList", childMapList);
                mapList.add(map);

                map = new HashMap<>();
                map.put("id", "c2");
                map.put("name", "文章管理");
                map.put("url", "/");
                childMapList = new ArrayList<>();
                childMap = new HashMap<>();
                childMap.put("name", "文章列表");
                childMap.put("url", "/toEvaluateList");
                childMapList.add(childMap);
                map.put("childList", childMapList);
                mapList.add(map);

                return mapList;
            }
        }
        return mapList;
    }
}
