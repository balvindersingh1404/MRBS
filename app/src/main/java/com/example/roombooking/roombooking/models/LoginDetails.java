package com.example.roombooking.roombooking.models;

/**
 * Created by balvinder on 17/11/16.
 */

public class LoginDetails {
    String name;
    String emailid;
    String mobile_no;
    String password;


    //For LOGIN
    public LoginDetails(String email, String password) {
        this.emailid = email;
        this.password = password;
    }
    // For REGISTRATION
    public LoginDetails(String name, String email, String mobile_no, String password) {
        this.name=name;
        this.emailid = email;
        this.mobile_no = mobile_no;
        this.password = password;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return emailid;
    }

    public void setEmail(String email) {
        this.emailid = email;
    }

    public LoginDetails(){

    }

}
