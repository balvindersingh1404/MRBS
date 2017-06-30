package com.example.roombooking.roombooking.landing;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.login.LoginActivity;
import com.example.roombooking.roombooking.myBookings.MyBookingActivity;
import com.example.roombooking.roombooking.roomBooking.RoomBookingActivity;
import com.example.roombooking.roombooking.roomList.RoomListingActivity;
import com.example.roombooking.roombooking.sharedPreferences.AppPreferences;
import com.example.roombooking.roombooking.sharedPreferences.MRBSApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String loginUserId;
    TextView username, userid;
    ImageView profilePic;
    Context context;
    DrawerLayout drawer;
    String loginName;
    String loginEmail;
    AppPreferences preference;
    Button gotoDate;
    private String gotoDateInput = "";
    int CAMERA_PIC_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        preference = new AppPreferences(context);

        if (MRBSApplication.getPref().getString(preference.month).equals("")) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            MRBSApplication.getPref().setString(preference.month, month + "");
            MRBSApplication.getPref().setString(preference.year, year + "");
            MRBSApplication.getPref().setString(preference.day, day + "");

        }

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.main_container, new MaterialCalendarFragment()).commit();
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gotoDate = (Button) toolbar.findViewById(R.id.gotoDate);
        gotoDate.setVisibility(View.VISIBLE);

        gotoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDate();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /* profilePic = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profiPic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
               // intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                intent.putExtra("return-data", true);
                startActivityForResult(intent,CAMERA_PIC_REQUEST);
            }
        });*/


        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        userid = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userid);

        loginName = MRBSApplication.getPref().getString(preference.name);
        loginEmail = MRBSApplication.getPref().getString(preference.email);
        loginUserId = MRBSApplication.getPref().getString(preference.id);

        username.setText(loginName);
        userid.setText(loginEmail);

    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK)
        {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            profilePic.setImageBitmap(bitmap);

        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        loginName = MRBSApplication.getPref().getString(preference.name);
        loginEmail = MRBSApplication.getPref().getString(preference.email);
        loginUserId = MRBSApplication.getPref().getString(preference.id);

        username.setText(loginName);
        userid.setText(loginEmail);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            MRBSApplication.getPref().setString(preference.month, "");
            MRBSApplication.getPref().setString(preference.year, "");
            MRBSApplication.getPref().setString(preference.day, "");

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.roomBookings) {
            Intent intent = new Intent(getApplicationContext(), RoomBookingActivity.class);
            startActivity(intent);

        } else if (id == R.id.rooms) {
            Intent intent = new Intent(getApplicationContext(), RoomListingActivity.class);
            startActivity(intent);

        } else if (id == R.id.myBookings) {
            Intent intent = new Intent(getApplicationContext(), MyBookingActivity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            AppPreferences preference = new AppPreferences(context);
            MRBSApplication.getPref().setString(preference.islogin, "false");
            MRBSApplication.getPref().setString(preference.month, "");
            MRBSApplication.getPref().setString(preference.year, "");
            MRBSApplication.getPref().setString(preference.day, "");
            startActivity(intent);
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void gotoDate() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Date (DD/MM/YYYY)");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("DD/MM/YYYY");
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotoDateInput = input.getText().toString();

                if (!gotoDateInput.trim().equals("") && gotoDateInput.length() == 10)

                {
                    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
                    sdfrmt.setLenient(false);
                    Date javaDate = null;
                    try {
                        javaDate = sdfrmt.parse(gotoDateInput);

                        String year = gotoDateInput.substring(6, 10);
                        String month = gotoDateInput.substring(3, 5);
                        String date = gotoDateInput.substring(0, 2);

                        Log.i("YEAR ", year);
                        Log.i("MONTH ", month);
                        Log.i("DATE ", date);

                        int monthInput = Integer.parseInt(month) - 1;
                        AppPreferences preference = new AppPreferences(context);
                        MRBSApplication.getPref().setString(preference.month, monthInput + "");
                        MRBSApplication.getPref().setString(preference.year, year);
                        MRBSApplication.getPref().setString(preference.day, date);

                        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                        startActivity(intent);
                        finish();


                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), "Invalid Format", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Format", Toast.LENGTH_SHORT).show();

                }

            }
        });

        builder.setNeutralButton("TODAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                MRBSApplication.getPref().setString(preference.month, month + "");
                MRBSApplication.getPref().setString(preference.year, year + "");
                MRBSApplication.getPref().setString(preference.day, day + "");

                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                startActivity(intent);
                finish();


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
