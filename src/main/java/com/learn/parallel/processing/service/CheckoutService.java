package com.learn.parallel.processing.service;

import com.learn.parallel.processing.domain.checkout.Cart;
import com.learn.parallel.processing.domain.checkout.CartItem;
import com.learn.parallel.processing.domain.checkout.CheckoutResponse;
import com.learn.parallel.processing.domain.checkout.CheckoutStatus;
import com.learn.parallel.processing.util.CommonUtil;
import com.learn.parallel.processing.util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutService {

    private final PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }


    public CheckoutResponse checkout(Cart cart){

        CommonUtil.startTimer();
        List<CartItem> priceValidationList = cart.getCartItemList()
                .parallelStream()
                .peek(cartItem -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInvalid);
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());
        CommonUtil.timeTaken();
        if(!priceValidationList.isEmpty()){
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }
        //double finalPrice = calculateFinalPrice(cart);
        double finalPrice = calculateFinalPriceUsingReduce(cart);
        LoggerUtil.log("Final Price: "+ finalPrice);

        return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice);
    }

    public double calculateFinalPrice(Cart cart){
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double calculateFinalPriceUsingReduce(Cart cart){
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0, Double::sum);
    }
}
