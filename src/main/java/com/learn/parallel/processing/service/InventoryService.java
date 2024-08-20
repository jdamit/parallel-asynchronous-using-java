package com.learn.parallel.processing.service;

import com.learn.parallel.processing.domain.Inventory;
import com.learn.parallel.processing.domain.ProductOption;
import com.learn.parallel.processing.util.CommonUtil;

public class InventoryService {
    public Inventory retrieveInventory(ProductOption productOption){
        CommonUtil.delay(500);
        return Inventory.builder()
                .count(2)
                .build();
    }
}
