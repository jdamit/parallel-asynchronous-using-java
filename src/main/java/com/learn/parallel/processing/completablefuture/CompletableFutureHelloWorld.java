package com.learn.parallel.processing.completablefuture;

import com.learn.parallel.processing.service.HelloWorldService;
import com.learn.parallel.processing.util.CommonUtil;
import com.learn.parallel.processing.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorld {

    private final HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorld(){
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld) //Responsive nature // runs this in a common fork-join pool
                .thenApply(String::toUpperCase) //Result from supplyAsync went to the thenAccept using message events.
                //.thenAccept((result)-> LoggerUtil.log("Result is "+result)) //Result from supplyAsync went to the thenAccept using message events.
                //.join()
        ;
        /*LoggerUtil.log("Done!");
        CommonUtil.delay(2000);*/
    }

    public String helloWorld_approach1(){
        CommonUtil.startTimer();
        String hello = helloWorldService.hello();
        String world = helloWorldService.world();
        String combinedResult = hello + world;
        combinedResult = combinedResult.toUpperCase();
        CommonUtil.timeTaken();
        return combinedResult;
    }

    public String helloWorld_multiple_async_calls(){
        CommonUtil.startTimer();
        CompletableFuture<String> completableFutureHello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> completableFutureWorld = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        String combinedResult = completableFutureHello
                .thenCombine(completableFutureWorld, (helloStr, worldStr) -> helloStr + worldStr)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return combinedResult;
    }

    public String helloWorld_3_async_calls(){
        CommonUtil.startTimer();
        CompletableFuture<String> completableFutureHello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> completableFutureWorld = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi Completable Future!";
        });
        String combinedResult = completableFutureHello
                .thenCombine(completableFutureWorld, (helloStr, worldStr) -> helloStr + worldStr)
                .thenCombine(completableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return combinedResult;
    }

    public CompletableFuture<String> helloWorld_thenCompose(){
        return CompletableFuture.supplyAsync(helloWorldService::hello)
                .thenCompose((previous) -> helloWorldService.worldFuture(previous)) //it depends on the result of the previous step.
                .thenApply(String::toUpperCase);
    }

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(helloWorldService::helloWorld) //Responsive nature // runs this in a common fork-join pool
                .thenApply(String::toUpperCase) //Result from supplyAsync went to the thenAccept using message events.
                .thenAccept((result)-> LoggerUtil.log("Result is "+result)) //Result from supplyAsync went to the thenAccept using message events.
        //        .join()
        ;
        LoggerUtil.log("Done!");
        CommonUtil.delay(2000);
    }
}
