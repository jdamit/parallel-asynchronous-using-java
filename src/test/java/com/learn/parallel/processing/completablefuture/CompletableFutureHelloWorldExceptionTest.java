package com.learn.parallel.processing.completablefuture;

import com.learn.parallel.processing.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    HelloWorldService helloWorldService;

    @InjectMocks
    CompletableFutureHelloWorldException completableFutureHelloWorldException;

    @Test
    void helloWorld_3_async_calls_handle() {

        //given
        Mockito.when(helloWorldService.hello()).thenThrow(new RuntimeException("Error occurred...!!!"));
        Mockito.when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = completableFutureHelloWorldException.helloWorld_3_async_calls_handle();

        //then
        assertEquals("HI WORLD! HI COMPLETABLE FUTURE!", result);

    }
    @Test
    void helloWorld_3_async_calls_handle_2() {

        //given
        Mockito.when(helloWorldService.hello()).thenThrow(new RuntimeException("Error occurred...!!!"));
        Mockito.when(helloWorldService.world()).thenThrow(new RuntimeException("Error occurred...!!!"));

        //when
        String result = completableFutureHelloWorldException.helloWorld_3_async_calls_handle();

        //then
        assertEquals(" UNIVERSE HI COMPLETABLE FUTURE!", result);

    }

    @Test
    void helloWorld_3_async_calls_handle_happy_scenario() {

        //given
        Mockito.when(helloWorldService.hello()).thenCallRealMethod();
        Mockito.when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = completableFutureHelloWorldException.helloWorld_3_async_calls_handle();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", result);

    }

    @Test
    void helloWorld_3_async_calls_exceptionally() {
        //given
        Mockito.when(helloWorldService.hello()).thenThrow(new RuntimeException("Error occurred...!!!"));
        Mockito.when(helloWorldService.world()).thenThrow(new RuntimeException("Error occurred...!!!"));

        //when
        String result = completableFutureHelloWorldException.helloWorld_3_async_calls_exceptionally();

        //then
        assertEquals(" UNIVERSE HI COMPLETABLE FUTURE!", result);
    }

    @Test
    void helloWorld_3_async_calls_exceptionally_happy_scenario() {

        //given
        Mockito.when(helloWorldService.hello()).thenCallRealMethod();
        Mockito.when(helloWorldService.world()).thenCallRealMethod();

        //when
        String result = completableFutureHelloWorldException.helloWorld_3_async_calls_exceptionally();

        //then
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", result);

    }
}