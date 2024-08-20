package com.learn.parallel.processing.parallelstreams;

import java.util.List;

public class ReduceExample {

    public int reduceSumParallelStream(List<Integer> inputList){
        return inputList.parallelStream()
                .reduce(0, Integer::sum);
    }

    public int reduceMultiplyParallelStream(List<Integer> inputList){
        return inputList.parallelStream()
                .reduce(1, (x,y) -> x*y);
    }
}
