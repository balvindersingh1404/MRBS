package com.example.roombooking.roombooking.models;

/**
 * Created by balvinder on 6/6/17.
 */

public class DurationListData {

    public DurationListData(String duration, double value) {
        this.duration = duration;
        this.value = value;
    }

    public String getDuration() {

        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private String duration;
    private double value;
}
