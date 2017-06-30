package com.example.roombooking.roombooking.models;

/**
 * Created by balvinder on 6/6/17.
 */

public class MeetingType {
    private String meetingtype;

    public MeetingType(String meetingtype, int value) {
        this.meetingtype = meetingtype;
        this.value = value;
    }

    private int value;

    public String getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(String meetingtype) {
        this.meetingtype = meetingtype;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
