package com.example.roombooking.roombooking.models;

/**
 * Created by balvinder on 23/5/17.
 */

public class AllBookingsRequest {
    private String month;
    private String year;
    private String roomid;
    private String userid;

    public AllBookingsRequest(String month, String year, String roomid, String userid) {
        this.month = month;
        this.year = year;
        this.roomid = roomid;
        this.userid = userid;
    }

    public String getUserid() {

        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public AllBookingsRequest(String month, String year, String roomid) {
        this.month = month;
        this.year = year;
        this.roomid = roomid;
    }

    public String getRoomid() {

        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public AllBookingsRequest(String month, String year) {
        this.month = month;
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
