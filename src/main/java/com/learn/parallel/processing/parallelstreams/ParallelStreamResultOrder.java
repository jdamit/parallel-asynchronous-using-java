package com.learn.parallel.processing.parallelstreams;

import com.learn.parallel.processing.util.LoggerUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParallelStreamResultOrder {

    public static List<Integer> listOrder(List<Integer> inputList){
        return inputList.parallelStream()
                .map(input -> input * 2)
                .collect(Collectors.toList());
    }

    public static Set<Integer> setOrder(Set<Integer> inputSet){
        return inputSet.parallelStream()
                .map(input -> input * 2)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        List<Integer> inputList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        LoggerUtil.log("Input List: "+inputList);
        List<Integer> resultList = listOrder(inputList);
        LoggerUtil.log("Result List: "+resultList);

        LoggerUtil.log("---------------------------------------------------");

        Set<Integer> inputSet = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        LoggerUtil.log("Input Set: "+inputSet);
        Set<Integer> resultSet = setOrder(inputSet);
        LoggerUtil.log("Result Set: "+resultSet);

    }
}
