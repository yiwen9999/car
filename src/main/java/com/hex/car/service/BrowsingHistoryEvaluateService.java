package com.hex.car.service;

import com.hex.car.domain.BrowsingHistoryEvaluate;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 上午10:00
 */
public interface BrowsingHistoryEvaluateService {
    BrowsingHistoryEvaluate saveBrowsingHistoryEvaluate(BrowsingHistoryEvaluate browsingHistoryEvaluate);

    void deleteBrowsingHistoryEvaluate(BrowsingHistoryEvaluate browsingHistoryEvaluate);

    BrowsingHistoryEvaluate findBrowsingHistoryEvaluateById(String id);
}
