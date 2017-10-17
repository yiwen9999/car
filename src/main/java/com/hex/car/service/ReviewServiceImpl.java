package com.hex.car.service;

import com.hex.car.domain.Review;
import com.hex.car.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: hexuan
 * Date: 2017/10/9
 * Time: 下午3:41
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }

    @Override
    public Review findReviewById(String id) {
        return reviewRepository.findOne(id);
    }
}
