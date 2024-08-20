package com.learn.parallel.processing.parallelstreams;

import com.learn.parallel.processing.util.CommonUtil;
import com.learn.parallel.processing.util.DataSet;
import com.learn.parallel.processing.util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ParallelStreamExample {

    public List<String> stringTransform(List<String> namesList){
        return namesList
                //.stream()
                .parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
        ParallelStreamExample parallelStreamExample = new ParallelStreamExample();
        CommonUtil.startTimer();
        List<String> resultList = parallelStreamExample.stringTransform(namesList);
        LoggerUtil.log("resultList: "+resultList);
        CommonUtil.timeTaken();
    }

    private String addNameLengthTransform(String name){
        CommonUtil.delay(500);
        return name.length()+" - "+name;
    }
}
