package com.hex.car.service;

import com.hex.car.domain.Review;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:40
 */
public interface ReviewService {
    Review saveReview(Review review);

    void deleteReview(Review review);

    Review findReviewById(String id);
}
