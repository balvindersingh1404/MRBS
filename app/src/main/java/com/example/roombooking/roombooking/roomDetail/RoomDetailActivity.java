package com.example.roombooking.roombooking.roomDetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.landing.MaterialCalendarFragment;

import java.util.ArrayList;

public class RoomDetailActivity extends AppCompatActivity {
    private TextView activityTitle;
    public  static String roomid=null;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction().add(R.id.main_container, new RoomDetailCalenderFragment()).commit();
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
