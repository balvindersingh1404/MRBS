package com.example.roombooking.roombooking.myBookings;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roombooking.roombooking.R;

public class MyBookingActivity extends AppCompatActivity {
    public static String roomid = null;
    private TextView activityTitle;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.main_container, new MyBookingCalenderFragment()).commit();
        }
        back = (ImageView) findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
