package com.learn.parallel.processing.parallelstreams;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReduceExampleTest {

    private final ReduceExample reduceExample = new ReduceExample();

    @Test
    void reduceSumParallelStream() {
        //given
        List<Integer> inputList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        //when
        int result = reduceExample.reduceSumParallelStream(inputList);

        //then
        assertEquals(45, result);

    }

    @Test
    void reduceSumParallelStreamUsingEmptyList() {
        //given
        List<Integer> inputList = new ArrayList<>();
        //when
        int result = reduceExample.reduceSumParallelStream(inputList);

        //then
        assertEquals(0, result);

    }

    @Test
    void reduceMultiplyParallelStream() {
        //given
        List<Integer> inputList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        //when
        int result = reduceExample.reduceMultiplyParallelStream(inputList);

        //then
        assertEquals(362880, result);

    }

    @Test
    void reduceMultiplyParallelStreamWithEmptyList() {
        //given
        List<Integer> inputList = new ArrayList<>();

        //when
        int result = reduceExample.reduceMultiplyParallelStream(inputList);

        //then
        assertEquals(1, result);

    }
}