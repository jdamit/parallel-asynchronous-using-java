package com.learn.parallel.processing.parallelstreams;

import com.learn.parallel.processing.util.CommonUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArraySpliteratorExample {

    public List<Integer> multiplyEachValue(List<Integer> inputLists, int multiplyValue, boolean isParallel){

        CommonUtil.startTimer();

        Stream<Integer> integerStream = inputLists.stream();
        if(isParallel) {
            integerStream.parallel();
        }

        List<Integer> resultList = integerStream
                .map(integer -> integer * multiplyValue)
                .collect(Collectors.toList());
        CommonUtil.timeTaken();
        return resultList;
    }
}
