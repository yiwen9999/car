package com.hex.car.service;

import com.hex.car.domain.Evaluate;
import com.hex.car.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:41
 */
public interface EvaluateService {
    Evaluate saveEvaluate(Evaluate evaluate);

    void deleteEvaluate(Evaluate evaluate);

    Evaluate findEvaluateById(String id);

    List<Evaluate> findAllEvaluateList();

    List<Evaluate> findTop4ByStateOrderByCreateTimeDesc(Integer state);

    List<Evaluate> findEvaluateListByCreateTimeAndNameAndIdentity(Date beginTime, Date endTime, String name, Shop shop);

    Page<Evaluate> findEvaluates(Map<String, Object> condition, PageRequest pageRequest);
}
