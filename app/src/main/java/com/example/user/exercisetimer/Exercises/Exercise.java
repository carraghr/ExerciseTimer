package com.example.user.exercisetimer.Exercises;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Exercise implements Serializable {

    private String name;
    private int times;

    private long restTime, holdTIme;

    public Exercise(String name, int times, long holdTIme, long restTime){
        this.name = name;
        this.times = times;
        this.restTime = restTime;
        this.holdTIme = holdTIme;
    }

    public String getName(){
        return name;
    }

    public String getTimes(){
        return ""+times;
    }

    public String getRestTime(){
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(restTime),
                TimeUnit.MILLISECONDS.toMinutes(restTime) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(restTime) % TimeUnit.MINUTES.toSeconds(1));
    }
    public String getHoldTIme(){
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(holdTIme),
                TimeUnit.MILLISECONDS.toMinutes(holdTIme) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(holdTIme) % TimeUnit.MINUTES.toSeconds(1));
    }

    public long getHoldTImeInMili() {
        return holdTIme;
    }

    public long getRestTImeInMili() {
        return restTime;
    }
}
