package com.hex.car.utils;


import com.hex.car.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: hexuan
 * Date: 2017/9/19
 * Time: 上午10:57
 */
public class HexUtil {
    public static Date formatBeginTimeString(String beginTime) {
        Date beginTimeDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (null != beginTime && !StringUtils.isEmpty(beginTime)) {
            try {
                beginTime += " 00:00:00";
                beginTimeDate = simpleDateFormat.parse(beginTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return beginTimeDate;
    }

    public static Date formatEndTimeString(String endTime) {
        Date endTimeDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (null != endTime && !StringUtils.isEmpty(endTime)) {
            try {
                endTime += " 23:59:59";
                endTimeDate = simpleDateFormat.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return endTimeDate;
    }

    public static User getUser(HttpServletRequest request) {
        User user = null;
        Object object = request.getSession().getAttribute("user");
        if (null != object) {
            user = (User) object;
        }
        return user;
    }

    public static PageRequest getPageRequest(Integer page, Integer size, String sortStr, String asc) {
        Sort sort;
        if (asc.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, sortStr);
        } else {
            sort = new Sort(Sort.Direction.DESC, sortStr);
        }
        return new PageRequest(page, size, sort);
    }

}
