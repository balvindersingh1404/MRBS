package com.example.roombooking.roombooking.models;

/**
 * Created by balvinder on 23/5/17.
 */

public class ConfirmBookingApiresponse {
    private Integer status;
    private String message;
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
