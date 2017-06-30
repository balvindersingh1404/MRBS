package com.example.roombooking.roombooking.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.roombooking.roombooking.sharedPreferences.AppPreferences;
import com.example.roombooking.roombooking.sharedPreferences.MRBSApplication;
import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.landing.LandingActivity;
import com.example.roombooking.roombooking.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    AppPreferences preference;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context=this;
        preference = new AppPreferences(context);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(MRBSApplication.getPref().getString(preference.islogin).equals("false")){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish() ;
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                    startActivity(intent);
                    finish();
                }}
        }, 2000);
    }
}
