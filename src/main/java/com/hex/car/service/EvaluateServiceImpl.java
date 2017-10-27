package com.hex.car.service;

import com.hex.car.domain.Evaluate;
import com.hex.car.domain.Shop;
import com.hex.car.repository.EvaluateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:42
 */
@Service
public class EvaluateServiceImpl implements EvaluateService {

    @Autowired
    private EvaluateRepository evaluateRepository;

    @Override
    public Evaluate saveEvaluate(Evaluate evaluate) {
        return evaluateRepository.save(evaluate);
    }

    @Override
    public void deleteEvaluate(Evaluate evaluate) {
        evaluateRepository.delete(evaluate);
    }

    @Override
    public Evaluate findEvaluateById(String id) {
        return evaluateRepository.findOne(id);
    }

    @Override
    public List<Evaluate> findAllEvaluateList() {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return evaluateRepository.findAll(sort);
    }

    @Override
    public List<Evaluate> findEvaluatesByProductShop(Shop shop) {
        return evaluateRepository.findEvaluatesByProductShop(shop);
    }
}
