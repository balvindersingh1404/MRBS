package com.example.roombooking.roombooking.models;


public class Data
{
    private String userid;
    private String name;
    private String mobile;
    private String password;
    private String email;

    private String roomid;
    private String noofseats;



    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }



    public String getNoofseats() {
        return noofseats;
    }

    public void setNoofseats(String noofseats) {
        this.noofseats = noofseats;
    }

    public String getUserid() { return this.userid; }

    public void setUserid(String userid) { this.userid = userid; }


    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }


    public String getMobile() { return this.mobile; }

    public void setMobile(String mobile) { this.mobile = mobile; }


    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }


    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }
}
