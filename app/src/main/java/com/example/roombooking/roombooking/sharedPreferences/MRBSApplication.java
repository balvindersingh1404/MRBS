package com.example.roombooking.roombooking.sharedPreferences;

import android.app.Application;
import android.content.Context;

public class MRBSApplication extends Application {

    public static MRBSApplication instance = null;
    public static AppPreferences preferences = null;

    public static Context getInstance() {
        if (null == instance) {
            instance = new MRBSApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       instance=this;
    }

    public static AppPreferences getPref(){
        if (null == preferences) {
            preferences = new AppPreferences(instance);
        }
        return preferences;
    }
    }

