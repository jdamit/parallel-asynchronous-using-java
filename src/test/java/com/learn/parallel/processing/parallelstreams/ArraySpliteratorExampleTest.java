package com.learn.parallel.processing.parallelstreams;

import com.learn.parallel.processing.util.CommonUtil;
import com.learn.parallel.processing.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArraySpliteratorExampleTest {

    ArraySpliteratorExample arraySpliteratorExample = new ArraySpliteratorExample();

    //@Test
    @RepeatedTest(5)
    void multiplyEachValue() {
        //given
        int size = 10000000;
        //CommonUtil.startTimer();
        ArrayList<Integer> integerArrayList = DataSet.generateArrayList(size);
        //CommonUtil.timeTaken();

        //when
        List<Integer> resultList = arraySpliteratorExample.multiplyEachValue(integerArrayList, 2, false);

        //then
        assertEquals(size, resultList.size());

    }

    @RepeatedTest(5)
    void multiplyEachValueParallel() {
        //given
        int size = 10000000;
        //CommonUtil.startTimer();
        ArrayList<Integer> integerArrayList = DataSet.generateArrayList(size);
        //CommonUtil.timeTaken();

        //when
        List<Integer> resultList = arraySpliteratorExample.multiplyEachValue(integerArrayList, 2, true);

        //then
        assertEquals(size, resultList.size());

    }
}