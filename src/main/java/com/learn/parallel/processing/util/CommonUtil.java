package com.learn.parallel.processing.util;

import org.apache.commons.lang3.time.StopWatch;

import java.time.Instant;

import static java.lang.Thread.sleep;

public class CommonUtil {

    public static StopWatch stopWatch = new StopWatch();
    public static Instant start;
    public static Instant end;

    public static void startTimer(){
        //stopWatch.start();
        start = Instant.now();
    }

    public static void stopTimer(){
        //stopWatch.stop();
    }

    public static void timeTaken(){
        //stopWatch.stop();
        end = Instant.now();
        LoggerUtil.log("Total time taken: "+ (end.toEpochMilli() - start.toEpochMilli()));
    }

    public static void delay(long delayMilliSeconds)  {
        try{
            sleep(delayMilliSeconds);
        }catch (Exception e){
            LoggerUtil.log("Exception is :" + e.getMessage());
        }

    }


}
