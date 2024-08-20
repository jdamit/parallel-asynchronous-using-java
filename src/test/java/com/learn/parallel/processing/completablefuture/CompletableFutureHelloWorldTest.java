package com.learn.parallel.processing.completablefuture;

import com.learn.parallel.processing.service.HelloWorldService;
import com.learn.parallel.processing.util.CommonUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    private final HelloWorldService helloWorldService = new HelloWorldService();

    private final CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloWorld() {
        //given

        //when
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld();

        //then
        completableFuture
                .thenAccept( str -> assertEquals("HELLO WORLD", str))
                .join();

    }

    @Test
    void helloWorld_approach1() {
        //given

        //when
        String result = completableFutureHelloWorld.helloWorld_approach1();

        //then
        assertEquals("HELLO WORLD!", result);

    }

    @Test
    void helloWorld_multiple_async_calls() {
        //given

        //when
        String result = completableFutureHelloWorld.helloWorld_multiple_async_calls();

        //then
        assertEquals("HELLO WORLD!", result);

    }

    @Test
    void helloWorld_3_async_calls() {
        //given

        //when
        String result = completableFutureHelloWorld.helloWorld_3_async_calls();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", result);

    }

    @Test
    void helloWorld_thenCompose() {
        CommonUtil.startTimer();
        //given

        //when
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld_thenCompose();

        //then
        completableFuture
                .thenAccept( str -> assertEquals("HELLO WORLD!", str))
                .join();

        CommonUtil.timeTaken();

    }

}