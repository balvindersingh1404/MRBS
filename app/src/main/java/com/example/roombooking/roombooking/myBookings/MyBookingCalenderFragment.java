package com.example.roombooking.roombooking.myBookings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.landing.LandingActivity;
import com.example.roombooking.roombooking.models.AllBookingsApiResponse;
import com.example.roombooking.roombooking.models.AllBookingsData;
import com.example.roombooking.roombooking.models.BookingByUserIdRequest;
import com.example.roombooking.roombooking.rest.ApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingCalenderFragment extends Fragment implements View.OnClickListener, GridView.OnItemClickListener {
    static final ArrayList<String> myarray = new ArrayList();

    // Saved Events Adapter
    protected static MyBookingSavedEventsAdapter mSavedEventsAdapter;
    protected static ListView mSavedEventsListView;
    protected static ArrayList<HashMap<String, Integer>> mSavedEventsPerDay;
    protected static ArrayList<Integer> mSavedEventDays;
    protected static int mNumEventsOnDay = 0;
    static List<AllBookingsData> listOfAllDataOfRoomDetail;
    static List<AllBookingsData> selectDateDataAfterDelete;
    private static Integer selectedDate;
    // Calendar Adapter
    public static MyBookingCalenderAdapter mMaterialCalendarAdapter;
    private static Call<AllBookingsApiResponse> call;
    private static Context context;
     static List<AllBookingsData> selectDateData;
   static String deleteTag = "";

    // Variables
    //Views
    ImageView mPrevious;
    ImageView mNext;
    TextView mMonthName;
    GridView mCalendar;
    Calendar cal = Calendar.getInstance();

    public static ArrayList<String> getMyarray() {
        return myarray;
    }

    // Saved Events
    public static void getSavedEventsForCurrentMonth(int currentMonthArg, int currentYearArg) {
        getBookingsByUser(currentMonthArg, currentYearArg, LandingActivity.loginUserId);
    }

    public static void showSavedEventsListView(int position) {
        Boolean savedEventsOnThisDay = false;
        selectedDate = -1;

        if (MyBookingCalender.mFirstDay != -1 && mSavedEventDays != null && mSavedEventDays.size
                () > 0) {
            selectedDate = position - (6 + MyBookingCalender.mFirstDay);
            Log.d("SELECTED_SAVED_DATE", String.valueOf(selectedDate));

            for (int i = 0; i < mSavedEventDays.size(); i++) {
                if (selectedDate == mSavedEventDays.get(i)) {
                    savedEventsOnThisDay = true;
                }
            }
        }

        Log.d("SAVED_EVENTS_BOOL", String.valueOf(savedEventsOnThisDay));

        if (savedEventsOnThisDay) {
            Log.d("POS", String.valueOf(selectedDate));
            if (mSavedEventsPerDay != null && mSavedEventsPerDay.size() > 0) {
                for (int i = 0; i < mSavedEventsPerDay.size(); i++) {
                    HashMap<String, Integer> x = mSavedEventsPerDay.get(i);
                    if (x.containsKey("day" + selectedDate)) {
                        mNumEventsOnDay = mSavedEventsPerDay.get(i).get("day" + selectedDate);
                        Log.d("NUM_EVENT_ON_DAY", String.valueOf(mNumEventsOnDay));
                    }
                }
            }
        } else {
            mNumEventsOnDay = -1;
        }

        if (mSavedEventsAdapter != null && mSavedEventsListView != null) {
            mSavedEventsAdapter.notifyDataSetChanged();

            // Scrolls back to top of ListView before refresh
            mSavedEventsListView.setSelection(0);
        }
    }


    public static void getBookingsByUser(int month, int year, String userid) {
        month = month + 1;
        Log.i("GETBOOKING BY USER", "CALLED");
        Log.i("Yearaaaaaa", year + "");

        BookingByUserIdRequest allBookingsRequest = new BookingByUserIdRequest(month + "", year + "", userid + "");
        call = ApiClient.getInstance().bookingByUserId(allBookingsRequest);
        call.enqueue(new Callback<AllBookingsApiResponse>() {
            @Override
            public void onResponse(Call<AllBookingsApiResponse> call, Response<AllBookingsApiResponse> response) {
                Log.i("Landing SUCCESS", response.body().toString());
                if (response.body().getStatus() == 1) {

                    listOfAllDataOfRoomDetail = response.body().getData();
                    mSavedEventsPerDay = new ArrayList<HashMap<String, Integer>>();

                    mSavedEventDays = new ArrayList<Integer>();

                    for (int i = 0; i < listOfAllDataOfRoomDetail.size(); i++) {

                        int eventPerDay = 0;

                        for (int j = 0; j < listOfAllDataOfRoomDetail.size(); j++) {
                            if (listOfAllDataOfRoomDetail.get(i).getBookingdate().equals(listOfAllDataOfRoomDetail.get(j).getBookingdate())) {
                                eventPerDay = eventPerDay + 1;
                            }

                        }

                        int day = Integer.parseInt(listOfAllDataOfRoomDetail.get(i).getBookingdate().substring(listOfAllDataOfRoomDetail.get(i).getBookingdate().length() - 2));
                        HashMap<String, Integer> dayInfo = new HashMap<String, Integer>();
                        dayInfo.put("day" + day, eventPerDay);
                        mSavedEventDays.add(day);
                        mSavedEventsPerDay.add(dayInfo);
                        Log.d("EVENTS_PER DAY", String.valueOf(dayInfo));

                    }

                    Log.d("SAVED_EVENT_DATES", String.valueOf(mSavedEventDays));
                    //    mSavedEventsAdapter.notifyDataSetChanged();
                    mMaterialCalendarAdapter.notifyDataSetChanged();

                    if (deleteTag.equals("delete")) {
                        getSelectedDateDataAfterDelete(MyBookingCalender.selectedDate);
                    }

                }

                if (response.body().getStatus() == 0) {

                    listOfAllDataOfRoomDetail = response.body().getData();
                    mSavedEventsPerDay = new ArrayList<HashMap<String, Integer>>();

                    mSavedEventDays = new ArrayList<Integer>();

                    for (int i = 0; i < listOfAllDataOfRoomDetail.size(); i++) {
                        int eventPerDay = 0;
                        for (int j = 0; j < listOfAllDataOfRoomDetail.size(); j++) {
                            if (listOfAllDataOfRoomDetail.get(i) == listOfAllDataOfRoomDetail.get(i)) {
                                eventPerDay = eventPerDay + 1;
                            }
                        }
                        int day = Integer.parseInt(listOfAllDataOfRoomDetail.get(i).getBookingdate().substring(listOfAllDataOfRoomDetail.get(i).getBookingdate().length() - 2));
                        HashMap<String, Integer> dayInfo = new HashMap<String, Integer>();
                        dayInfo.put("day" + day, eventPerDay);
                        mSavedEventDays.add(day);
                        mSavedEventsPerDay.add(dayInfo);
                        Log.d("EVENTS_PER DAY", String.valueOf(dayInfo));

                    }

                    Log.d("SAVED_EVENT_DATES", String.valueOf(mSavedEventDays));
                    //     mSavedEventsAdapter.notifyDataSetChanged();
                    mMaterialCalendarAdapter.notifyDataSetChanged();

                }

            }


            @Override
            public void onFailure(Call<AllBookingsApiResponse> call, Throwable t) {
                Log.i("API RESPONSE FAIL", t.toString());
            }
        });

    }

    public static void getSelectedDateDataAfterDelete(String selectedDate) {


        Log.i("SELECTED DATE DATA", "CALLED");
        if (listOfAllDataOfRoomDetail != null) {
            selectDateDataAfterDelete = new ArrayList<AllBookingsData>();
            for (int i = 0; i < listOfAllDataOfRoomDetail.size(); i++) {
                if (selectedDate.equals(listOfAllDataOfRoomDetail.get(i).getBookingdate())) {
                    selectDateDataAfterDelete.add(listOfAllDataOfRoomDetail.get(i));
                }
            }

            if (selectDateDataAfterDelete != null) {
                Log.i("helllo", "helo");
                mSavedEventsAdapter = new MyBookingSavedEventsAdapter(context, selectDateDataAfterDelete);
                mSavedEventsListView.setAdapter(mSavedEventsAdapter);
                mSavedEventsAdapter.notifyDataSetChanged();
                mMaterialCalendarAdapter.notifyDataSetChanged();
            }
            else{
                mSavedEventsAdapter = new MyBookingSavedEventsAdapter(context, selectDateDataAfterDelete);
                mSavedEventsListView.setAdapter(mSavedEventsAdapter);
                mSavedEventsAdapter.notifyDataSetChanged();
                mMaterialCalendarAdapter.notifyDataSetChanged();
            }

        }
        deleteTag="";
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        getBookingsByUser(month, year, LandingActivity.loginUserId);

//        Date cDate = new Date();
//        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
//        getSelectedDateData(fDate);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        if (rootView != null) {
            mSavedEventsListView = (ListView) rootView.findViewById(R.id.saved_events_listView);

            // Get Calendar info
            MyBookingCalender.getInitialCalendarInfo(context);
            getSavedEventsForCurrentMonth(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));

            // Previous ImageView
            mPrevious = (ImageView) rootView.findViewById(R.id.material_calendar_previous);
            if (mPrevious != null) {
                mPrevious.setOnClickListener(this);
            }

            // Month name TextView
            mMonthName = (TextView) rootView.findViewById(R.id.material_calendar_month_name);
            if (mMonthName != null) {
                if (cal != null) {
                    mMonthName.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                            Locale.getDefault()) + " " + cal.get(Calendar.YEAR));
                }
            }

            // Next ImageView
            mNext = (ImageView) rootView.findViewById(R.id.material_calendar_next);
            if (mNext != null) {
                mNext.setOnClickListener(this);
            }

            // GridView for custom Calendar
            mCalendar = (GridView) rootView.findViewById(R.id.material_calendar_gridView);
            if (mCalendar != null) {
                mCalendar.setOnItemClickListener(this);
                mMaterialCalendarAdapter = new MyBookingCalenderAdapter(getActivity());
                mCalendar.setAdapter(mMaterialCalendarAdapter);


                // Set current day to be auto selected when first opened
                if (MyBookingCalender.mCurrentDay != -1 && MyBookingCalender.mFirstDay != -1) {
                    int startingPosition = 6 + MyBookingCalender.mFirstDay;
                    int currentDayPosition = startingPosition + MyBookingCalender.mCurrentDay;

                    Log.d("INITIAL_SELECTED_POSITION", String.valueOf(currentDayPosition));
                    mCalendar.setItemChecked(currentDayPosition, true);

                    if (mMaterialCalendarAdapter != null) {
                        mMaterialCalendarAdapter.notifyDataSetChanged();
                    }
                }
            }

            // ListView for saved events in calendar
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mSavedEventsListView != null) {
            int today = MyBookingCalender.mCurrentDay + 6 + MyBookingCalender.mFirstDay;
        }
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.material_calendar_previous:
                    //month--;
                    MyBookingCalender.previousOnClick(mPrevious, mMonthName, mCalendar, mMaterialCalendarAdapter);
                    break;

                case R.id.material_calendar_next:
                    MyBookingCalender.nextOnClick(mNext, mMonthName, mCalendar, mMaterialCalendarAdapter);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.material_calendar_gridView:
                MyBookingCalender.selectCalendarDay(mMaterialCalendarAdapter, position);

                getSelectedDateData(MyBookingCalender.selectedDate);
                mNumEventsOnDay = -1;

                showSavedEventsListView(position);
                break;

            default:
                break;
        }
    }

    public static  void getSelectedDateData(String selectedDate) {
        Log.i("SELECTED DATE DATA", "CALLED");
        if (listOfAllDataOfRoomDetail != null) {
            selectDateData = new ArrayList<AllBookingsData>();
            for (int i = 0; i < listOfAllDataOfRoomDetail.size(); i++) {
                if (selectedDate.equals(listOfAllDataOfRoomDetail.get(i).getBookingdate())) {
                    selectDateData.add(listOfAllDataOfRoomDetail.get(i));
                }
            }

            if (selectDateData != null) {
                Log.i("helllo", "helo");
                mSavedEventsAdapter = new MyBookingSavedEventsAdapter(context, selectDateData);
                mSavedEventsListView.setAdapter(mSavedEventsAdapter);
//                mSavedEventsListView.setOnItemClickListener(MyBookingCalenderFragment.this);
                mSavedEventsAdapter.notifyDataSetChanged();
                mMaterialCalendarAdapter.notifyDataSetChanged();
            }

        }
    }

}


