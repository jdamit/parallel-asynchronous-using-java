package com.learn.parallel.processing.service;


import com.learn.parallel.processing.domain.checkout.CartItem;
import com.learn.parallel.processing.util.LoggerUtil;

import static com.learn.parallel.processing.util.CommonUtil.delay;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        LoggerUtil.log("isCartItemInvalid: "+ cartItem);
        delay(500);
        return cartId == 7 || cartId == 9 || cartId == 11;
    }
}
