package com.example.roombooking.roombooking.roomBooking;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.landing.LandingActivity;
import com.example.roombooking.roombooking.landing.MaterialCalendar;
import com.example.roombooking.roombooking.landing.MaterialCalendarFragment;
import com.example.roombooking.roombooking.models.AvailableRoomsRequest;
import com.example.roombooking.roombooking.models.AvailableRoomsResposnseData;
import com.example.roombooking.roombooking.models.ConfirmBookingApiresponse;
import com.example.roombooking.roombooking.models.DurationListData;
import com.example.roombooking.roombooking.models.MeetingType;
import com.example.roombooking.roombooking.models.RoomsAvailableApiResponse;
import com.example.roombooking.roombooking.rest.ApiClient;
import com.example.roombooking.roombooking.sharedPreferences.AppPreferences;
import com.example.roombooking.roombooking.sharedPreferences.MRBSApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomBookingActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 0;
    public static String roomid;
    public static double duration;
    public static int meetingType;
    public static String endTime;
    LinearLayout selectDateLayout, selectStartTimeLayout, selectDurationLayout, selectRoomLayout, selectTitleLayout, selectRecurringLayout, selectEndDateLayout, selectDescriptionLayout, selectMeetingTypeLayout;
    TextView setDateTextView, setStartTimeTextView, setDurationTextView, setRoomTextView, setMeetingTitleView, setRecurring, setEndDate, setMeetingTypeTextView, setDescriptionTextView;
    ImageView back;
    Button confirmButton;
    ProgressDialog progress;
    Context context;
    int recurringType;
    int isreoccuring = 0;

    // SET the end date
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private Call<RoomsAvailableApiResponse> call;
    private Call<ConfirmBookingApiresponse> call1;
    private int pYear;
    private int pMonth;
    private int pDay;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();

                }
            };


    //***********************************************************validateTime function****************************************************
    public static boolean validateTime(String bookingStartTime, String bookingEndTime) {
        boolean afterValidate = false;
        try {

            Calendar calander = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String currentTimeString = simpleDateFormat.format(calander.getTime());

            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(bookingStartTime);
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(bookingEndTime);
            Date current = new SimpleDateFormat("HH:mm:ss").parse(currentTimeString);

            Log.i("START TIME", time1 + "");
            Log.i("END TIME", time2 + "");
            Log.i("CURRENT TIME", current + "");


            if (time1.after(current) && time2.after(current) && !(time1.equals(time2))) {
                afterValidate = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return afterValidate;
    }


    //************************************************************validate Date is after on not the current date**********************************
    public static boolean isDateAfter(String today, String startDate, String endDate, String statTiming, String endTiming) {
        boolean validator = false;
        try {
            String myFormatString = "yyyy-MM-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);

            Date todayDate = df.parse(today);
            Date startingDate = df.parse(startDate);
            Date endingDate = df.parse(endDate);


            Log.i("TODAY DATEee", todayDate.toString());
            Log.i("START DATEee", startingDate.toString());
            Log.i("END DATEee", endingDate.toString());


            if (startingDate.after(todayDate) && endingDate.after(todayDate) || endingDate.equals(todayDate) && endingDate.after(startingDate) || endingDate.equals(startingDate)) {
                Log.i("GREATRER", "DDDDDDDDDDDD");
                validator = true;
            } else if (startingDate.equals(todayDate) && (endingDate.after(todayDate) || endingDate.equals(todayDate)) && (endingDate.after(startingDate) || endingDate.equals(startingDate))) {
                Log.i("EQUALS", "FFFFFFFFFFFF");
                validator = validateTime(statTiming, endTiming);
                Log.i("VALIDATOR TIME", validator + "");
            }
            Log.i("VALIDATOR TIME22222", validator + "");

            return validator;

        } catch (Exception e) {

            return validator;
        }
    }


    // **********************************************************************onCreate function*******************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booking);
        context = this;
        init();
        registerListener();

    }

    //***********************************************************init function ****************************************************
    public void init() {
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        confirmButton = (Button) findViewById(R.id.confirmButton);

        back = (ImageView) findViewById(R.id.back);

        selectDateLayout = (LinearLayout) findViewById(R.id.selectDateLayout);
        selectStartTimeLayout = (LinearLayout) findViewById(R.id.selectStartTimeLayout);
        selectDurationLayout = (LinearLayout) findViewById(R.id.selectDurationLayout);
        selectRoomLayout = (LinearLayout) findViewById(R.id.selectRoomLayout);
        selectTitleLayout = (LinearLayout) findViewById(R.id.selectTitleLayout);
        selectRecurringLayout = (LinearLayout) findViewById(R.id.selectRecurringLayout);
        selectEndDateLayout = (LinearLayout) findViewById(R.id.selectEndDateLayout);
        selectMeetingTypeLayout = (LinearLayout) findViewById(R.id.selectMeetingTypeLayout);
        selectDescriptionLayout = (LinearLayout) findViewById(R.id.selectDescriptionLayout);

        setDateTextView = (TextView) findViewById(R.id.setDateTextView);
        setStartTimeTextView = (TextView) findViewById(R.id.setStartTimeTextView);
        setDurationTextView = (TextView) findViewById(R.id.setDurationTextView);
        setRoomTextView = (TextView) findViewById(R.id.setRoomTextView);
        setMeetingTitleView = (TextView) findViewById(R.id.setMeetingTitleView);
        setRecurring = (TextView) findViewById(R.id.setRecurring);
        setEndDate = (TextView) findViewById(R.id.setEndDate);
        setMeetingTypeTextView = (TextView) findViewById(R.id.setMeetingTypeTextView);
        setDescriptionTextView = (TextView) findViewById(R.id.setDescriptionTextView);


    }


    //***********************************************************register listerner function *********************************************
    public void registerListener() {


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        selectDateLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setRoomTextView.setText("");                                                             //back button
                showDialog(DATE_DIALOG_ID);

            }
        });


        selectStartTimeLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setRoomTextView.setText("");
                final Calendar c = Calendar.getInstance();                                               //start time
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                setStartTimeTextView.setText(hourOfDay + ":" + minute + ":00");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });


        selectDurationLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setRoomTextView.setText("");
                List<DurationListData> listDataList = new ArrayList<DurationListData>();
                listDataList.add(new DurationListData("0.5 Hour", 0.5));
                listDataList.add(new DurationListData("1 Hour", 1.0));
                listDataList.add(new DurationListData("1.5 Hour", 1.5));                                      // duration
                listDataList.add(new DurationListData("2 Hour", 2.0));
                listDataList.add(new DurationListData("2.5 Hour", 2.5));
                listDataList.add(new DurationListData("3 Hour", 3.0));
                listDataList.add(new DurationListData("3.5 Hour", 3.5));
                listDataList.add(new DurationListData("4 Hour", 4.0));
                listDataList.add(new DurationListData("Full Day", 24.0));

                showDurationList(listDataList);

            }
        });

        selectMeetingTypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<MeetingType> listDataList = new ArrayList<MeetingType>();
                listDataList.add(new MeetingType("Internal", 1));
                listDataList.add(new MeetingType("External", 2));

                showMeetingType(listDataList);


            }
        });


        selectRoomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(setDateTextView.getText()) && !TextUtils.isEmpty(setStartTimeTextView.getText()) && !TextUtils.isEmpty(setDurationTextView.getText())) {
                    getRoomList();
                }                                                                                                 //select room
            }
        });

        selectRecurringLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                builderSingle.setTitle("Recurring Type");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("None");
                arrayAdapter.add("Daily");
                arrayAdapter.add("Weekly");
                arrayAdapter.add("Bi-Weekly");                                                                 // recurring type
                arrayAdapter.add("Monthly");
                arrayAdapter.add("Yearly");
                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setRecurring.setText(arrayAdapter.getItem(which));
//                        Date cDate = new Date();
//                        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                        recurringType = which;
                        if (recurringType != 0) {
                            selectEndDateLayout.setVisibility(View.VISIBLE);
                            setEndDate.setText(setDateTextView.getText());
                        } else {
                            selectEndDateLayout.setVisibility(View.GONE);
                            setEndDate.setText(setDateTextView.getText());
                        }

                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });


        selectEndDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RoomBookingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),                                       //end date
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(setDateTextView.getText())) {
                    setDateTextView.setBackground(getResources().getDrawable(R.drawable.borderred));
                }
                //confirm button
                if (TextUtils.isEmpty(setStartTimeTextView.getText())) {
                    setStartTimeTextView.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (TextUtils.isEmpty(setDurationTextView.getText())) {
                    setDurationTextView.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (TextUtils.isEmpty(setRoomTextView.getText())) {
                    setRoomTextView.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (TextUtils.isEmpty(setMeetingTitleView.getText())) {
                    setMeetingTitleView.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (TextUtils.isEmpty(setRecurring.getText())) {
                    setRecurring.setBackground(getResources().getDrawable(R.drawable.borderred));
                }

                if (!TextUtils.isEmpty(setDateTextView.getText()) && !TextUtils.isEmpty(setStartTimeTextView.getText()) && !TextUtils.isEmpty(setDurationTextView.getText()) & !TextUtils.isEmpty(setRoomTextView.getText()) && !TextUtils.isEmpty(setMeetingTitleView.getText()) && !TextUtils.isEmpty(setRecurring.getText())) {

                    Date cDate = new Date();
                    String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                    String bookingStartDate = setDateTextView.getText().toString();
                    String bookingEndDate = setEndDate.getText().toString();

                    String bookingStartTime = setStartTimeTextView.getText().toString();
                    String bookingEndTime = endTime;

                    Log.i("TODAY DATE", todayDate);
                    Log.i("START DATE", bookingStartDate);
                    Log.i("END DATE", bookingEndDate);

                    if (isDateAfter(todayDate, bookingStartDate, bookingEndDate, bookingStartTime, bookingEndTime)) {
                        confirmBooking();
                    } else {
                        setDateTextView.setText("");
                        setDateTextView.setBackground(getResources().getDrawable(R.drawable.borderred));

                        setStartTimeTextView.setText("");
                        setStartTimeTextView.setBackground(getResources().getDrawable(R.drawable.borderred));


                        setDurationTextView.setText("");
                        setDurationTextView.setBackground(getResources().getDrawable(R.drawable.borderred));


                    }
                }
            }
        });

        selectTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTitle();                                                                                //title
            }
        });

    }

    private void updateDisplay() {
        String date = "";
        pMonth++;
        if (pMonth < 10 && pDay >= 10) {
            date = pYear + "-" + 0 + pMonth + "-" + pDay;
        }

        if (pMonth < 10 && pDay < 10) {
            date = pYear + "-" + 0 + pMonth + "-" + 0 + pDay;
        }

        if (pDay < 10 && pMonth >= 10) {
            date = pYear + "-" + pMonth + "-" + 0 + pDay;
        }

        if (pDay >= 10 && pMonth >= 10) {
            date = pYear + "-" + pMonth + "-" + pDay;
        }

        setDateTextView.setText(new StringBuilder(date));
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }

    //********************************************************getRoomList function*****************************************************
    public void getRoomList() {
        showProgressDialog();
        calculateEndTime();
        String bookingdate = setDateTextView.getText().toString();
        String starttime = setStartTimeTextView.getText().toString();
        String endtime = endTime;

        Log.i("DATADADADAD", bookingdate + starttime + endtime);

        AvailableRoomsRequest availableRoomsDetail = new AvailableRoomsRequest(bookingdate, starttime, endtime);

        call = ApiClient.getInstance().getAvailableRooms(availableRoomsDetail);

        call.enqueue(new Callback<RoomsAvailableApiResponse>() {
            @Override
            public void onResponse(Call<RoomsAvailableApiResponse> call, Response<RoomsAvailableApiResponse> response) {
                Log.i("SUCCESS11", response.body().toString());
                showList(response.body().getData());

            }

            @Override
            public void onFailure(Call<RoomsAvailableApiResponse> call, Throwable t) {
                Log.i("API RESPONSE FAIL", t.toString());
                networkIssue();
            }
        });

    }

    // ***********************************************************Confirm booking***********************************************
    public void confirmBooking() {

        showProgressDialog();
        String userid = LandingActivity.loginUserId;
        String bookingdate = setDateTextView.getText().toString();
        String starttime = setStartTimeTextView.getText().toString();
        String title = setMeetingTitleView.getText().toString();
        String bookingenddate = setEndDate.getText().toString();
        String meetingTypeInput = Integer.toString(meetingType);
        String description = setDescriptionTextView.getText().toString();

        if (!setRecurring.getText().toString().equals("None"))
            isreoccuring = 1;

        String isRecurringRequest = Integer.toString(isreoccuring);
        String recurringTypeRequest = Integer.toString(recurringType);

        Log.i("DATADADADAD", bookingdate + starttime + endTime);

        AvailableRoomsRequest availableRoomsDetail = new AvailableRoomsRequest(userid, roomid, bookingdate, starttime, endTime, title, isRecurringRequest, recurringTypeRequest, bookingenddate, meetingTypeInput, description);

        call1 = ApiClient.getInstance().confirmBooking(availableRoomsDetail);

        call1.enqueue(new Callback<ConfirmBookingApiresponse>() {
            @Override
            public void onResponse(Call<ConfirmBookingApiresponse> call, Response<ConfirmBookingApiresponse> response) {
                progress.dismiss();

                if (response.body().getStatus() == 1) {
                    Toast.makeText(context, "Booking Done", Toast.LENGTH_SHORT).show();
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    MaterialCalendarFragment.getBookings(month,year);
                    finish();

                } else if (response.body().getStatus() == 0) {
                    Toast.makeText(context, "Slot Not Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ConfirmBookingApiresponse> call, Throwable t) {
                Log.i("API RESPONSE FAIL", t.toString());
                networkIssue();
            }
        });

    }

    // *************************************************************progress Dialog***********************************************
    public void showProgressDialog() {
        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    // ************************************************************Network Issues function***********************************************
    public void networkIssue() {
        progress.dismiss();
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage("Please check your Internet Connection.");
        dlgAlert.setTitle("Error Message...");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    public void showList(final List<AvailableRoomsResposnseData> list) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Available Rooms");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        for (int i = 0; i < list.size(); i++) {
            arrayAdapter.add(list.get(i).getName());
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setRoomTextView.setText(arrayAdapter.getItem(which));
                roomid = list.get(which).getRoomid();
                dialog.dismiss();
                progress.dismiss();
            }
        });
        builderSingle.show();
    }


    //********************************************************************showMeetingType function***************************
    public void showMeetingType(final List<MeetingType> list) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Meeting Type");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        for (int i = 0; i < list.size(); i++) {
            arrayAdapter.add(list.get(i).getMeetingtype());
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setMeetingTypeTextView.setText(arrayAdapter.getItem(which));
                meetingType = list.get(which).getValue();
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    //********************************************************************showDurationList function***************************
    public void showDurationList(final List<DurationListData> list) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Duration");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        for (int i = 0; i < list.size(); i++) {
            arrayAdapter.add(list.get(i).getDuration());
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setDurationTextView.setText(arrayAdapter.getItem(which));
                duration = list.get(which).getValue();
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }


    //***************************************************************bookingConfirmedPopup***************************************
    public void bookingConfirmedPopup() {
        progress.dismiss();
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage("Booking Successfull");
        dlgAlert.setTitle("Success");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                });
    }

    //******************************************************************setTitle function*************************************************
    public void setTitle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        final EditText input = new EditText(this);
        FrameLayout container = new FrameLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.fab_margin);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.fab_margin);
        input.setLayoutParams(params);
        container.addView(input);
        builder.setView(container);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setMeetingTitleView.setText(input.getText().toString());
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

    //********************************************************************set end date*****************************************
    private void updateLabel() {

        setEndDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(myCalendar.get(Calendar.YEAR)).append("-")
                        .append(myCalendar.get(Calendar.MONTH) + 1).append("-")
                        .append(myCalendar.get(Calendar.DAY_OF_MONTH))
        );
    }

    public void calculateEndTime() {

        int minutes = 0;

        if (setDurationTextView.getText().equals("0.5 Hour")) {
            minutes = 30;
        }
        if (setDurationTextView.getText().equals("1 Hour")) {
            minutes = 60;
        }

        if (setDurationTextView.getText().equals("1.5 Hour")) {
            minutes = 90;
        }

        if (setDurationTextView.getText().equals("2 Hour")) {
            minutes = 120;
        }

        if (setDurationTextView.getText().equals("2.5 Hour")) {
            minutes = 150;
        }

        if (setDurationTextView.getText().equals("3 Hour")) {
            minutes = 180;
        }

        if (setDurationTextView.getText().equals("3.5 Hour")) {
            minutes = 210;
        }

        if (setDurationTextView.getText().equals("4 Hour")) {
            minutes = 240;
        }

        if (setDurationTextView.getText().equals("Full Day")) {
            minutes = 250;
        }
        try {
            String startTime = setStartTimeTextView.getText() + "";
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = dateFormat.parse(startTime);
            Calendar gc = new GregorianCalendar();
            gc.setTime(date);
            gc.add(Calendar.MINUTE, minutes);
            Date date2 = gc.getTime();

            if(minutes==250){
                endTime = "23:59:00";
            }
            else{
                endTime = date2.getHours() + ":" + date2.getMinutes() + ":" + date2.getSeconds();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}