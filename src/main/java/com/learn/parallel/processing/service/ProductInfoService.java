package com.learn.parallel.processing.service;

import com.learn.parallel.processing.domain.ProductInfo;
import com.learn.parallel.processing.domain.ProductOption;

import java.util.List;

import static com.learn.parallel.processing.util.CommonUtil.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId) {
        delay(1000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99),
                new ProductOption(3, "64GB", "Black", 699.99),
                new ProductOption(4, "128GB", "Black", 749.99),
                new ProductOption(5, "64GB", "Black", 699.99),
                new ProductOption(6, "128GB", "Black", 749.99),
                new ProductOption(7, "64GB", "Black", 699.99),
                new ProductOption(8, "128GB", "Black", 749.99),
                new ProductOption(9, "64GB", "Black", 699.99),
                new ProductOption(10, "128GB", "Black", 749.99),
                new ProductOption(11, "64GB", "Black", 699.99),
                new ProductOption(12, "128GB", "Black", 749.99),
                new ProductOption(13, "64GB", "Black", 699.99),
                new ProductOption(14, "128GB", "Black", 749.99),
                new ProductOption(15, "64GB", "Black", 699.99),
                new ProductOption(16, "128GB", "Black", 749.99));
        return ProductInfo.builder().productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
