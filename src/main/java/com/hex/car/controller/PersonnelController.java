package com.hex.car.controller;

import com.hex.car.domain.Personnel;
import com.hex.car.enums.ResultEnum;
import com.hex.car.service.ImgUserService;
import com.hex.car.service.PersonnelService;
import com.hex.car.service.UserService;
import com.hex.car.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 4s店相关controller
 * User: hexuan
 * Date: 2017/10/13
 * Time: 下午2:26
 */
@RestController
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImgUserService imgUserService;

    @Value("${web.upload-path}")
    private String path;

    /**
     * 停启用用户
     *
     * @param id 4s店id
     * @return
     */
    @PostMapping(value = "/updatePersonnelState")
    public Object updatePersonnelState(String id) {
        Personnel personnel = personnelService.findPersonnelById(id);
        if (null == personnel) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        if (2 == personnel.getState()) {
            personnel.setState(new Integer(-1));
            personnel.getUser().setState(new Integer(-1));
            userService.saveUser(personnel.getUser());
        } else {
            personnel.setState(new Integer(2));
            personnel.getUser().setState(new Integer(2));
            userService.saveUser(personnel.getUser());
        }
        return ResultUtil.success(personnelService.savePersonnel(personnel));
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return
     */
    @PostMapping(value = "/deletePersonnel")
    public Object deletePersonnel(String id) {
        if (null == id || id.equals("")) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        Personnel personnel = personnelService.findPersonnelById(id);
        if (null == personnel) {
            return ResultUtil.error(ResultEnum.ERROR_PARAM.getCode(), ResultEnum.ERROR_PARAM.getMsg());
        }
        File deleteFile;
        deleteFile = new File(path + personnel.getUser().getImgUser().getFileName());
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
        personnelService.deletePersonnel(personnel);
        return ResultUtil.success();
    }

    @PostMapping(value = "/searchPersonnelList")
    public Object searchPersonnelList(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "1000") Integer size,
                                      @RequestParam(defaultValue = "createTime") String sortStr,
                                      @RequestParam(defaultValue = "desc") String asc,
                                      String name, String mobile) {
        Sort sort;
        if (asc.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, sortStr);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortStr);
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Map<String, Object> condition = new HashMap<>();
        condition.put("name", name);
        condition.put("mobile", mobile);
        Map<String, Object> map;
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Personnel personnel : personnelService.findPersonnels(condition, pageRequest).getContent()) {
            map = new HashMap<>();
            if (null != personnel.getUser()) {
                map.put("username", personnel.getUser().getUsername());
            }
            map.put("id", personnel.getId());
            map.put("name", personnel.getName());
            map.put("nickname", personnel.getNickname());
            map.put("mobile", personnel.getMobile());
            map.put("createTime", personnel.getCreateTime());
            map.put("state", personnel.getState());
            mapList.add(map);
        }
        return ResultUtil.success(mapList);
    }

}
