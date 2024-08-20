package com.learn.parallel.processing.executor;

import com.learn.parallel.processing.domain.Product;
import com.learn.parallel.processing.domain.ProductInfo;
import com.learn.parallel.processing.domain.Review;
import com.learn.parallel.processing.service.ProductInfoService;
import com.learn.parallel.processing.service.ReviewService;

import java.util.concurrent.*;

import static com.learn.parallel.processing.util.CommonUtil.stopWatch;
import static com.learn.parallel.processing.util.LoggerUtil.log;

public class ProductServiceUsingExecutor {

    static ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceUsingExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws ExecutionException, InterruptedException, TimeoutException {
        stopWatch.start();

        Future<ProductInfo> productInfoFuture = executorService.submit(()->productInfoService.retrieveProductInfo(productId));
        Future<Review> reviewFuture = executorService.submit(()->reviewService.retrieveReviews(productId));

        //ProductInfo productInfo = productInfoFuture.get();
        ProductInfo productInfo = productInfoFuture.get(2, TimeUnit.SECONDS);
        Review  review = reviewFuture.get();

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingExecutor productService = new ProductServiceUsingExecutor(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
        executorService.shutdown();

    }
}
