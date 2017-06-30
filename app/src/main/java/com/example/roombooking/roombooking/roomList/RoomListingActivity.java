package com.example.roombooking.roombooking.roomList;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.rest.ApiClient;
import com.example.roombooking.roombooking.roomDetail.RoomDetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListingActivity extends AppCompatActivity implements MyAdapter.SelectedItem {
    public static String roomid;
    Context context;
    ProgressDialog progress;
    List<Datum> data;
    Call<Example> call;
    ImageView back;
    private ListView lv;
    Datum selectedRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        back = (ImageView) findViewById(R.id.back);
        lv = (ListView) findViewById(R.id.roomList);
        context = this;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        callgetroomList();

    }


    private void callgetroomList() {
        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        call = ApiClient.getInstance().getRoomList();
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, final Response<Example> response) {
                progress.dismiss();
                data = response.body().getData();
                final MyAdapter myAdapter = new MyAdapter(context, data,RoomListingActivity.this);

                lv.setAdapter(myAdapter);
//                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        selectedRoom=(Datum) myAdapter.getItemAtPosition(position);
//
//                        roomid=selectedRoom.getRoomid();
//                        Intent intent = new Intent(context, RoomDetailActivity.class);
//                        startActivity(intent);
//                    }
//                });
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    @Override
    public void seletedItem(int positin) {
        selectedRoom=data.get(positin);
        roomid=selectedRoom.getRoomid();
        Intent intent = new Intent(context, RoomDetailActivity.class);
        startActivity(intent);
    }
}