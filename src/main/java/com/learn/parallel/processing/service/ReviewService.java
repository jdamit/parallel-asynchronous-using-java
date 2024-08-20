package com.learn.parallel.processing.service;

import com.learn.parallel.processing.domain.Review;

import static com.learn.parallel.processing.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}
