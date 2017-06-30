package com.example.roombooking.roombooking.models;

/**
 * Created by balvinder on 9/6/17.
 */

public class DeleteMeetingRequest {
    private String bookingid;

    public DeleteMeetingRequest(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getBookingid() {

        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }


}
