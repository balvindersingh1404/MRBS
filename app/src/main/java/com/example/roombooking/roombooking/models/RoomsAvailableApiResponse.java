package com.example.roombooking.roombooking.models;

import java.util.List;

/**
 * Created by balvinder on 23/5/17.
 */

public class RoomsAvailableApiResponse {
    private Integer status;
    private String message;
    private List<AvailableRoomsResposnseData> data = null;

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

    public List<AvailableRoomsResposnseData> getData() {
        return data;
    }

    public void setData(List<AvailableRoomsResposnseData> data) {
        this.data = data;
    }


}
