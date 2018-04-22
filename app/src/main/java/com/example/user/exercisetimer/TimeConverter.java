package com.example.user.exercisetimer;

import java.util.concurrent.TimeUnit;

public class TimeConverter{
    public static long toMilliSeconds(long hour,long minute, long second){
        return TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(hour) + TimeUnit.MINUTES.toSeconds(minute))+TimeUnit.SECONDS.toMillis(second);
    }
}