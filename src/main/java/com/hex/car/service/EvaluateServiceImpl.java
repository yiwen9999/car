package com.hex.car.service;

import com.hex.car.domain.Evaluate;
import com.hex.car.repository.EvaluateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
