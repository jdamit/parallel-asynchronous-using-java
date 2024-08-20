package com.learn.parallel.processing.completablefuture;

import com.learn.parallel.processing.service.HelloWorldService;
import com.learn.parallel.processing.util.CommonUtil;
import com.learn.parallel.processing.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorldException {

    private final HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public String helloWorld_3_async_calls_handle(){
        CommonUtil.startTimer();
        CompletableFuture<String> completableFutureHello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> completableFutureWorld = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi Completable Future!";
        });
        String combinedResult = completableFutureHello
                .handle((result, exception)->{
                    if(exception != null){
                        LoggerUtil.log("Exception is : "+exception.getMessage());
                        return "Hi";
                    }else{
                        return result;
                    }
                })
                .thenCombine(completableFutureWorld, (helloStr, worldStr) -> helloStr + worldStr)
                .handle((result, exception)->{
                    if(exception != null){
                        LoggerUtil.log("Exception after world is : "+exception.getMessage());
                        return " Universe";
                    }else{
                        return result;
                    }
                })
                .thenCombine(completableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return combinedResult;
    }

    public String helloWorld_3_async_calls_exceptionally(){
        CommonUtil.startTimer();
        CompletableFuture<String> completableFutureHello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> completableFutureWorld = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " Hi Completable Future!";
        });
        String combinedResult = completableFutureHello
                .exceptionally((exception)->{
                    LoggerUtil.log("Exception is : "+exception.getMessage());
                    return "Hi";
                })
                .thenCombine(completableFutureWorld, (helloStr, worldStr) -> helloStr + worldStr)
                .exceptionally((exception)->{
                    LoggerUtil.log("Exception after world is : "+exception.getMessage());
                    return " Universe";
                })
                .thenCombine(completableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();
        return combinedResult;
    }
}
