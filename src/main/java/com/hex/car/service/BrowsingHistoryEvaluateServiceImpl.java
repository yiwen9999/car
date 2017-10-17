package com.hex.car.service;

import com.hex.car.domain.BrowsingHistoryEvaluate;
import com.hex.car.repository.BrowsingHistoryEvaluateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:03
 */
@Service
public class BrowsingHistoryEvaluateServiceImpl implements BrowsingHistoryEvaluateService{

    @Autowired
    private BrowsingHistoryEvaluateRepository browsingHistoryEvaluateRepository;

    @Override
    public BrowsingHistoryEvaluate saveBrowsingHistoryEvaluate(BrowsingHistoryEvaluate browsingHistoryEvaluate) {
        return browsingHistoryEvaluateRepository.save(browsingHistoryEvaluate);
    }

    @Override
    public void deleteBrowsingHistoryEvaluate(BrowsingHistoryEvaluate browsingHistoryEvaluate) {
        browsingHistoryEvaluateRepository.delete(browsingHistoryEvaluate);
    }

    @Override
    public BrowsingHistoryEvaluate findBrowsingHistoryEvaluateById(String id) {
        return browsingHistoryEvaluateRepository.findOne(id);
    }
}
