package com.example.roombooking.roombooking.models;

import java.util.List;

/**
 * Created by balvinder on 23/5/17.
 */

public class AllBookingsApiResponse {
    private Integer status;
    private String message;
    private List<AllBookingsData> data = null;

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

    public List<AllBookingsData> getData() {
        return data;
    }

    public void setData(List<AllBookingsData> data) {
        this.data = data;
    }

}
