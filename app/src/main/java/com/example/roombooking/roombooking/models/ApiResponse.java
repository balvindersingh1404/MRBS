package com.example.roombooking.roombooking.models;


import java.util.ArrayList;
import java.util.List;

public class ApiResponse {

    String message;
    int status;
    Data data;
    private List<Data> datum = new ArrayList<Data>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Data> getDatum() {
        return datum;
    }

    public void setDatum(List<Data> datum) {
        this.datum = datum;
    }
}