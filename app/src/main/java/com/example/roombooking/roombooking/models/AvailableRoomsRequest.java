package com.example.roombooking.roombooking.models;

/**
 * Created by balvinder on 23/5/17.
 */

public class AvailableRoomsRequest {

    private String bookingdate;
    private String starttime;
    private String endtime;
    private String userid;
    private String roomid;
    private String title;
    private String isreoccuring;
    private String reoccuringtype;
    private String bookingenddate;
    private  String description;
    private String meetingtype;

    public String getBookingenddate() {
        return bookingenddate;
    }

    public void setBookingenddate(String bookingenddate) {
        this.bookingenddate = bookingenddate;
    }

    public String getIsreoccuring() {
        return isreoccuring;
    }

    public void setIsreoccuring(String isreoccuring) {
        this.isreoccuring = isreoccuring;
    }

    public String getReoccuringtype() {
        return reoccuringtype;
    }

    public void setReoccuringtype(String reoccuringtype) {
        this.reoccuringtype = reoccuringtype;
    }

    //for getting room list
    public AvailableRoomsRequest(String bookingdate, String starttime, String endtime) {
        this.bookingdate = bookingdate;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    //for Confirm booking
    public AvailableRoomsRequest(String userid,String roomid,String bookingdate, String starttime, String endtime,String title , String isreoccuring,String reoccuringtype , String bookingenddate ,String meetingtype, String description) {
        this.userid=userid;
        this.roomid=roomid;
        this.bookingdate = bookingdate;
        this.starttime = starttime;
        this.endtime = endtime;
        this.title=title;
        this.isreoccuring=isreoccuring;
        this.reoccuringtype=reoccuringtype;
        this.bookingenddate=bookingenddate;
        this.description=description;
        this.meetingtype=meetingtype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(String meetingtype) {
        this.meetingtype = meetingtype;
    }
}
