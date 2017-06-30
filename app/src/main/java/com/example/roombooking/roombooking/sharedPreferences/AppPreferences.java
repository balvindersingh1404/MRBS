package com.example.roombooking.roombooking.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
 static   SharedPreferences mPrefs ;
 static   SharedPreferences.Editor editor;

    public static final String APPPREFERENCES = "MyPrefs" ;

    public static final String name ="name";
    public static final String email ="email";
    public static final String id ="id";
    public static final String islogin="islogin";
    public static final String month="month";
    public static final String year="year";
    public static final String day="day";



    public static String islogin() {
        return islogin;
    }

    public static String getId() {
        return id;
    }


    public static SharedPreferences getmPrefs() {
        return mPrefs;
    }

    public static void setmPrefs(SharedPreferences mPrefs) {
        AppPreferences.mPrefs = mPrefs;
    }

    public static String getEmail() {
        return email;
    }

    public static String getName() {
        return name;
    }

    public AppPreferences(Context context){
         mPrefs = context.getSharedPreferences(APPPREFERENCES,context.MODE_PRIVATE);
        editor = mPrefs.edit();
    }
    public  void setString(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }

    public  String getString(String key) {

        return   mPrefs.getString(key,"");

    }
}
