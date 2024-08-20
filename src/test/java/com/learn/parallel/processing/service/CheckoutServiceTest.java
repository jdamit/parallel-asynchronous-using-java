package com.learn.parallel.processing.service;

import com.learn.parallel.processing.domain.checkout.Cart;
import com.learn.parallel.processing.domain.checkout.CheckoutResponse;
import com.learn.parallel.processing.domain.checkout.CheckoutStatus;
import com.learn.parallel.processing.util.DataSet;
import com.learn.parallel.processing.util.LoggerUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void no_of_cores(){
        LoggerUtil.log("No of cores: "+Runtime.getRuntime().availableProcessors());
    }

    @Test
    void parallelism(){
        //give

        //when
        LoggerUtil.log("Parallelism: "+ ForkJoinPool.getCommonPoolParallelism());

        //then
    }

    @Test
    void checkout_6_items_test() {
        //given
        Cart cart = DataSet.createCart(6);

        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
        assertTrue(checkoutResponse.getFinalRate() > 0);
    }

    @Test
    void checkout_22_items_test() {
        //given
        Cart cart = DataSet.createCart(22);

        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_44_items_test() {
        //given
        Cart cart = DataSet.createCart(44);

        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_176_items_test() {
        //given
        Cart cart = DataSet.createCart(176);

        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_176_items_with_modify_parallelism_test() {
        //given
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "176");
        Cart cart = DataSet.createCart(176);

        //when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }
}