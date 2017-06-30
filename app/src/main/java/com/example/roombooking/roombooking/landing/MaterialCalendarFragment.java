package com.example.roombooking.roombooking.landing;

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
import com.example.roombooking.roombooking.models.AllBookingsApiResponse;
import com.example.roombooking.roombooking.models.AllBookingsData;
import com.example.roombooking.roombooking.models.AllBookingsRequest;
import com.example.roombooking.roombooking.models.SearchByDate;
import com.example.roombooking.roombooking.rest.ApiClient;
import com.example.roombooking.roombooking.sharedPreferences.AppPreferences;
import com.example.roombooking.roombooking.sharedPreferences.MRBSApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialCalendarFragment extends Fragment implements View.OnClickListener, GridView.OnItemClickListener {
    static final ArrayList<String> myarray = new ArrayList();

    // Saved Events Adapter
    protected static SavedEventsAdapter mSavedEventsAdapter;
    protected static ListView mSavedEventsListView;
    protected static ArrayList<HashMap<String, Integer>> mSavedEventsPerDay;
    protected static ArrayList<Integer> mSavedEventDays;
    protected static int mNumEventsOnDay = 0;
    static List<AllBookingsData> listOfAllData;
    private static Integer selectedDate;
    // Calendar Adapter
    private static MaterialCalendarAdapter mMaterialCalendarAdapter;
    private static Call<AllBookingsApiResponse> call;
    private static Context context;
    List<AllBookingsData> selectDateData;
    AppPreferences preference;

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
    protected static void getSavedEventsForCurrentMonth(int currentMonthArg, int currentYearArg) {
        getBookings(currentMonthArg, currentYearArg);
    }

    protected static void showSavedEventsListView(int position) {
        Boolean savedEventsOnThisDay = false;
        selectedDate = -1;

        if (MaterialCalendar.mFirstDay != -1 && mSavedEventDays != null && mSavedEventDays.size
                () > 0) {
            selectedDate = position - (6 + MaterialCalendar.mFirstDay);
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

    public static void getBookings(int month, int year) {
        month = month + 1;

        Log.i("MONTHH", month + "");
        Log.i("Year", year + "");

        AllBookingsRequest allBookingsRequest = new AllBookingsRequest(month + "", year + "");
        call = ApiClient.getInstance().allBookings(allBookingsRequest);
        call.enqueue(new Callback<AllBookingsApiResponse>() {
            @Override
            public void onResponse(Call<AllBookingsApiResponse> call, Response<AllBookingsApiResponse> response) {
                Log.i("Landing SUCCESS", response.body().toString());
                if (response.body().getStatus() == 1) {

                    listOfAllData = response.body().getData();
                    mSavedEventsPerDay = new ArrayList<HashMap<String, Integer>>();

                    mSavedEventDays = new ArrayList<Integer>();

                    for (int i = 0; i < listOfAllData.size(); i++) {

                        int eventPerDay = 0;

                        for (int j = 0; j < listOfAllData.size(); j++) {
                            if (listOfAllData.get(i).getBookingdate().equals(listOfAllData.get(j).getBookingdate())) {
                                eventPerDay = eventPerDay + 1;
                            }
         //                   Log.i("EVENTCOUNT", eventPerDay + " DAY" + listOfAllData.get(i).getBookingdate());

                        }

                        int day = Integer.parseInt(listOfAllData.get(i).getBookingdate().substring(listOfAllData.get(i).getBookingdate().length() - 2));
                        HashMap<String, Integer> dayInfo = new HashMap<String, Integer>();
                        dayInfo.put("day" + day, eventPerDay);
                        mSavedEventDays.add(day);
                        mSavedEventsPerDay.add(dayInfo);
 //                       Log.d("EVENTS_PER DAY", String.valueOf(dayInfo));

                    }

 //                   Log.d("SAVED_EVENT_DATES", String.valueOf(mSavedEventDays));
                    //         mSavedEventsAdapter.notifyDataSetChanged();
                    mMaterialCalendarAdapter.notifyDataSetChanged();

                }

                if (response.body().getStatus() == 0) {

                    listOfAllData = response.body().getData();
                    mSavedEventsPerDay = new ArrayList<HashMap<String, Integer>>();

                    mSavedEventDays = new ArrayList<Integer>();

                    for (int i = 0; i < listOfAllData.size(); i++) {
                        int eventPerDay = 0;
                        for (int j = 0; j < listOfAllData.size(); j++) {
                            if (listOfAllData.get(i) == listOfAllData.get(i)) {
                                eventPerDay = eventPerDay + 1;
                            }
                        }
                        int day = Integer.parseInt(listOfAllData.get(i).getBookingdate().substring(listOfAllData.get(i).getBookingdate().length() - 2));
                        HashMap<String, Integer> dayInfo = new HashMap<String, Integer>();
                        dayInfo.put("day" + day, eventPerDay);
                        mSavedEventDays.add(day);
                        mSavedEventsPerDay.add(dayInfo);
 //                       Log.d("EVENTS_PER DAY", String.valueOf(dayInfo));

                    }

 //                   Log.d("SAVED_EVENT_DATES", String.valueOf(mSavedEventDays));
       //             mSavedEventsAdapter.notifyDataSetChanged();
                    mMaterialCalendarAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<AllBookingsApiResponse> call, Throwable t) {
                Log.i("API RESPONSE FAIL", t.toString());

            }
        });

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        preference = new AppPreferences(context);

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
            // Get Calendar info
            MaterialCalendar.getInitialCalendarInfo(context);

            int month = Integer.parseInt(MRBSApplication.getPref().getString(preference.month));
            int year = Integer.parseInt(MRBSApplication.getPref().getString(preference.year));

            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.YEAR,year);

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
                mMaterialCalendarAdapter = new MaterialCalendarAdapter(getActivity());
                mCalendar.setAdapter(mMaterialCalendarAdapter);


                // Set current day to be auto selected when first opened
                if (MaterialCalendar.mCurrentDay != -1 && MaterialCalendar.mFirstDay != -1) {
                    int startingPosition = 6 + MaterialCalendar.mFirstDay;
                    int currentDayPosition = startingPosition + MaterialCalendar.mCurrentDay;

  //                  Log.d("INITIAL_SELECTED_POSITION", String.valueOf(currentDayPosition));
                    mCalendar.setItemChecked(currentDayPosition, true);

                    if (mMaterialCalendarAdapter != null) {
                        mMaterialCalendarAdapter.notifyDataSetChanged();
                    }
                }
            }

            // ListView for saved events in calendar
            mSavedEventsListView = (ListView) rootView.findViewById(R.id.saved_events_listView);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mSavedEventsListView != null) {

            // Show current day saved events on load
            int today = MaterialCalendar.mCurrentDay + 6 + MaterialCalendar.mFirstDay;
            showSavedEventsListView(today);
        }
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.material_calendar_previous:
                    //month--;
                    MaterialCalendar.previousOnClick(mPrevious, mMonthName, mCalendar, mMaterialCalendarAdapter);
                    break;

                case R.id.material_calendar_next:
                    MaterialCalendar.nextOnClick(mNext, mMonthName, mCalendar, mMaterialCalendarAdapter);
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
                MaterialCalendar.selectCalendarDay(mMaterialCalendarAdapter, position);
            //    selectDateData.clear();
                getBookingByDate(MaterialCalendar.selectedDate,position);

//                mNumEventsOnDay = -1;
//                    showSavedEventsListView(position);
                break;

            default:
                break;
        }
    }


    public void getBookingByDate(final String selectedDate , final int position) {

        SearchByDate searchByDate = new SearchByDate(selectedDate);
        call = ApiClient.getInstance().searchByDate(searchByDate);

        call.enqueue(new Callback<AllBookingsApiResponse>() {
            @Override
            public void onResponse(Call<AllBookingsApiResponse> call, Response<AllBookingsApiResponse> response) {
                Log.i("Landing SUCCESS", response.body().toString());
                if (response.body().getStatus() == 1) {
                    // progress.dismiss();
                    selectDateData = new ArrayList<AllBookingsData>();
                    selectDateData = response.body().getData();
                    for (int i = 0; i < selectDateData.size(); i++) {
 //                       Log.i("DATATATATAT", selectDateData.get(i).getBookingid());
                    }
                    //  progress.dismiss();
                    if (selectDateData != null) {

                        Log.i("helllo", "helo");
                        mSavedEventsAdapter = new SavedEventsAdapter(getActivity().getApplicationContext(), selectDateData);
                        mSavedEventsListView.setAdapter(mSavedEventsAdapter);
                        mSavedEventsListView.setOnItemClickListener(MaterialCalendarFragment.this);
                    }

                    mNumEventsOnDay = -1;
                    showSavedEventsListView(position);
                    mSavedEventsAdapter.notifyDataSetChanged();
                    mMaterialCalendarAdapter.notifyDataSetChanged();


                }
                if (response.body().getStatus() == 0) {

                }

            }

            @Override
            public void onFailure(Call<AllBookingsApiResponse> call, Throwable t) {
                Log.i("API RESPONSE FAIL", t.toString());
            }
        });

    }


}


