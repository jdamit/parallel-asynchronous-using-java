package com.learn.parallel.processing.completablefuture;

import com.learn.parallel.processing.domain.Inventory;
import com.learn.parallel.processing.domain.Product;
import com.learn.parallel.processing.service.InventoryService;
import com.learn.parallel.processing.service.ProductInfoService;
import com.learn.parallel.processing.service.ReviewService;
import com.learn.parallel.processing.util.CommonUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUsingCompletableFutureTest {

    private final ProductInfoService productInfoService = new ProductInfoService();
    private final ReviewService reviewService = new ReviewService();
    private final InventoryService inventoryService = new InventoryService();

    ProductServiceUsingCompletableFuture productServiceUsingCF = new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);

    @Test
    void retrieveProductDetails() {
        //given
        String productId = "ABC123";

        //when
        Product product = productServiceUsingCF.retrieveProductDetails(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());


    }

    @Test
    void retrieveProductDetails_approach2() {
        //given
        String productId = "ABC123";

        //when
        CompletableFuture<Product> productCompletableFuture = productServiceUsingCF.retrieveProductDetails_approach2(productId);
        CommonUtil.startTimer();
        Product product = productCompletableFuture.join();
        CommonUtil.timeTaken();

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());


    }

    @Test
    void retrieveProductDetailsWithInventory() {
        //given
        String productId = "ABC123";

        //when
        Product product = productServiceUsingCF.retrieveProductDetailsWithInventory(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(
                productOption -> {
                    assertTrue( 2 == productOption.getInventory().getCount());
                }
        );
        assertNotNull(product.getReview());

    }

    @Test
    void retrieveProductDetailsWithInventory_approach2() {
        //given
        String productId = "ABC123";

        //when
        Product product = productServiceUsingCF.retrieveProductDetailsWithInventory_approach2(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(
                productOption -> {
                    assertTrue( 2 == productOption.getInventory().getCount());
                }
        );
        assertNotNull(product.getReview());
    }
}