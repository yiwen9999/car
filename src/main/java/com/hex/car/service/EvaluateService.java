package com.hex.car.service;

import com.hex.car.domain.Evaluate;
import com.hex.car.domain.Shop;

import java.util.List;

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

    List<Evaluate> findEvaluatesByProductShop(Shop shop);
}
