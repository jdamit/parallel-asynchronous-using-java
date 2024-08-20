package com.learn.parallel.processing.completablefuture;

import com.learn.parallel.processing.domain.*;
import com.learn.parallel.processing.service.InventoryService;
import com.learn.parallel.processing.service.ProductInfoService;
import com.learn.parallel.processing.service.ReviewService;
import com.learn.parallel.processing.util.CommonUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learn.parallel.processing.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetails(String productId) {
        CommonUtil.startTimer();

        CompletableFuture<ProductInfo> productInfoCF = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCF = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCF.thenCombine(reviewCF, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); //this will block the thread

        CommonUtil.timeTaken();
        return product;
    }

    /**
     * When you are building a server based application then always return a Completable Future.
     * It is the responsibility of the call to perform the blocking operations and get he results.
     * @param productId
     * @return
     */
    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {
        CommonUtil.startTimer();

        CompletableFuture<ProductInfo> productInfoCF = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCF = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        CompletableFuture<Product> product = productInfoCF.thenCombine(reviewCF, (productInfo, review) -> new Product(productId, productInfo, review));

        CommonUtil.timeTaken();
        return product;
    }

    public Product retrieveProductDetailsWithInventory(String productId) {
        CommonUtil.startTimer();

        CompletableFuture<ProductInfo> productInfoCF = CompletableFuture.
                supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply((productInfo -> {
                    productInfo.setProductOptions(updateInventory(productInfo));
                    return productInfo;
                }));
        CompletableFuture<Review> reviewCF = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCF.thenCombine(reviewCF, (productInfo, review) -> new Product(productId, productInfo, review))
                .join();

        CommonUtil.timeTaken();
        return product;
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {

        List<ProductOption> productOptions = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    Inventory inventory = inventoryService.retrieveInventory(productOption);
                    productOption.setInventory(inventory);
                    return productOption;
                }).collect(Collectors.toList());
        return productOptions;
    }

    public Product retrieveProductDetailsWithInventory_approach2(String productId) {
        CommonUtil.startTimer();

        CompletableFuture<ProductInfo> productInfoCF = CompletableFuture.
                supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply((productInfo -> {
                    productInfo.setProductOptions(updateInventory_approach2(productInfo));
                    return productInfo;
                }));
        CompletableFuture<Review> reviewCF = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCF.thenCombine(reviewCF, (productInfo, review) -> new Product(productId, productInfo, review))
                .join();

        CommonUtil.timeTaken();
        return product;
    }

    private List<ProductOption> updateInventory_approach2(ProductInfo productInfo) {

        List<CompletableFuture<ProductOption>> cfProductOptions = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    return CompletableFuture.supplyAsync(()-> inventoryService.retrieveInventory(productOption))
                            .thenApply(inventory -> {
                                productOption.setInventory(inventory);
                                return productOption;
                            });
                })
                .collect(Collectors.toList());
        return cfProductOptions.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
