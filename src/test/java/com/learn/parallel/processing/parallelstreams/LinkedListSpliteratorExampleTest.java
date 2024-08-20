package com.learn.parallel.processing.parallelstreams;

import com.learn.parallel.processing.util.CommonUtil;
import com.learn.parallel.processing.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

    //@Test
    @RepeatedTest(5)
    void multiplyEachValue() {
        //given
        int size = 10000000;
        //CommonUtil.startTimer();
        LinkedList<Integer> integerArrayList = DataSet.generateIntegerLinkedList(size);
        //CommonUtil.timeTaken();

        //when
        List<Integer> resultList = linkedListSpliteratorExample.multiplyEachValue(integerArrayList, 2, false);

        //then
        assertEquals(size, resultList.size());

    }

    //@Test
    @RepeatedTest(5)
    void multiplyEachValueParallel() {
        //given
        int size = 10000000;
        //CommonUtil.startTimer();
        LinkedList<Integer> integerArrayList = DataSet.generateIntegerLinkedList(size);
        //CommonUtil.timeTaken();

        //when
        List<Integer> resultList = linkedListSpliteratorExample.multiplyEachValue(integerArrayList, 2, true);

        //then
        assertEquals(size, resultList.size());

    }
}