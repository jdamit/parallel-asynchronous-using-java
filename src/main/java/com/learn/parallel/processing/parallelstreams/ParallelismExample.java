package com.learn.parallel.processing.parallelstreams;

import java.util.List;
import java.util.stream.Collectors;


public class ParallelismExample {

    public static void main(String[] args) {

        List<String> namesList = List.of("Bob", "Jamie", "Jill", "Rick");
        System.out.println("namesList : " + namesList);
        List<String> namesListUpperCase = namesList
                .parallelStream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("namesListUpperCase : " + namesListUpperCase);

    }
}
