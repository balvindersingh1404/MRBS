package com.example.roombooking.roombooking.models;

/**
 * Created by balvinder on 29/5/17.
 */

public class BookingByUserIdRequest {

    private String month;
    private String year;
    private String userid;

    public BookingByUserIdRequest(String month, String year, String userid) {
        this.month = month;
        this.year = year;
        this.userid = userid;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
