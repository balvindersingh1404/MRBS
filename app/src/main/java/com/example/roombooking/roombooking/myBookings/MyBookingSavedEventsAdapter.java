package com.example.roombooking.roombooking.myBookings;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.models.AllBookingsApiResponse;
import com.example.roombooking.roombooking.models.AllBookingsData;
import com.example.roombooking.roombooking.rest.ApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingSavedEventsAdapter extends BaseAdapter {
    private static MyBookingSavedEventsAdapter.ViewHolder mHolder;
    List<AllBookingsData> selectDateData;
    ProgressDialog progress;
    String bookingid;
    private Context mContext;
    private Call<AllBookingsApiResponse> call;

    // Constructor
    public MyBookingSavedEventsAdapter(Context context, List<AllBookingsData> selectDateData) {

        mContext = context;
        this.selectDateData = selectDateData;

    }

    @Override
    public int getCount() {
        if (MyBookingCalenderFragment.mNumEventsOnDay != 0 || MyBookingCalenderFragment.mNumEventsOnDay != -1) {
            return MyBookingCalenderFragment.mNumEventsOnDay;
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (selectDateData != null && position < selectDateData.size()) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_material_saved_event_item_mybooking, parent, false);

                mHolder = new MyBookingSavedEventsAdapter.ViewHolder();

                if (convertView != null) {

                    TextView eventTitle, organiser, venueText, startTimeText, endTimeText;

                    mHolder.eventTitle = (TextView) convertView.findViewById(R.id.eventTitle);
                    mHolder.organiser = (TextView) convertView.findViewById(R.id.organiser);
                    mHolder.venueText = (TextView) convertView.findViewById(R.id.venueText);
                    mHolder.startTimeText = (TextView) convertView.findViewById(R.id.startTimeText);
                    mHolder.endTimeText = (TextView) convertView.findViewById(R.id.endTimeText);
                    mHolder.setBookingId = (TextView) convertView.findViewById(R.id.setBookingId);
                    mHolder.deleteImage = (ImageView) convertView.findViewById(R.id.deleteImage);

                    convertView.setTag(mHolder);

                }
            } else {
                mHolder = (MyBookingSavedEventsAdapter.ViewHolder) convertView.getTag();
            }

            // Animates in each cell when added to the ListView
            Animation animateIn = AnimationUtils.loadAnimation(mContext, R.anim.listview_top_down);
            if (convertView != null && animateIn != null) {
                convertView.startAnimation(animateIn);
            }


            AllBookingsData data = selectDateData.get(position);
            if (data != null) {

                if (mHolder.eventTitle != null) {
                    mHolder.eventTitle.setText(data.getTitle());
                    if (data.getMeetingtype().equals("EXTERNAL"))
                        convertView.setBackgroundColor(mContext.getResources().getColor(R.color.external));
                    else
                        convertView.setBackgroundColor(mContext.getResources().getColor(R.color.internal));
                }
                if (mHolder.organiser != null) {
                    mHolder.organiser.setText(data.getUsername());
                }
                if (mHolder.venueText != null) {
                    mHolder.venueText.setText(data.getRoomname());
                }
                if (mHolder.startTimeText != null) {
                    mHolder.startTimeText.setText(data.getStarttime());
                }
                if (mHolder.endTimeText != null) {
                    mHolder.endTimeText.setText(data.getEndtime());
                }
                if (mHolder.setBookingId != null) {
                    mHolder.setBookingId.setText(data.getBookingid());
                }
                mHolder.deleteImage.setTag(data.getBookingid());

                mHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag() != null) {
                            String bookingId = (String) v.getTag();
                            deleteBooking(bookingId, position);

                        }
                    }
                });

            }

        }

        return convertView;
    }

    public void deleteBooking(final String bookingid, final int position) {

        AllBookingsData allBookingsData = new AllBookingsData(bookingid);

        call = ApiClient.getInstance().deleteUserBooking(allBookingsData);

        call.enqueue(new Callback<AllBookingsApiResponse>() {
            @Override
            public void onResponse(Call<AllBookingsApiResponse> call, Response<AllBookingsApiResponse> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(mContext, "Meeting Deleted Successfully " + bookingid, Toast.LENGTH_SHORT).show();

                    Log.i(" MAIN LIST SIZE ", MyBookingCalenderFragment.listOfAllDataOfRoomDetail.size() + "");
                    for (int i = 0; i < MyBookingCalenderFragment.listOfAllDataOfRoomDetail.size(); i++) {
                        Log.i("DATA ", i + " " + MyBookingCalenderFragment.listOfAllDataOfRoomDetail.get(i).getBookingid());
                        if (bookingid.equals(MyBookingCalenderFragment.listOfAllDataOfRoomDetail.get(i).getBookingid())) {
                            MyBookingCalenderFragment.listOfAllDataOfRoomDetail.remove(i);
                        }
                    }


                    Log.i("LIST SIZE ", MyBookingCalenderFragment.selectDateData.size() + "");
                    for (int i = 0; i < MyBookingCalenderFragment.selectDateData.size(); i++) {
                        Log.i("DATA ", i + " " + MyBookingCalenderFragment.selectDateData.get(i).getBookingid());
                        if (bookingid.equals(MyBookingCalenderFragment.selectDateData.get(i).getBookingid())) {
                            MyBookingCalenderFragment.selectDateData.remove(i);
                        }
                        if (MyBookingCalenderFragment.selectDateData.size() >= 0 ||MyBookingCalenderFragment.selectDateData!=null ){
                            Log.i("SIZEEEE ",MyBookingCalenderFragment.selectDateData.size()+"");
                            Log.i("SIZEEEE22   ",MyBookingCalenderFragment.listOfAllDataOfRoomDetail.size()+"");
                           // if(MyBookingCalenderFragment.mNumEventsOnDay==2)
                            MyBookingCalenderFragment.mNumEventsOnDay=MyBookingCalenderFragment.mNumEventsOnDay-1;
                            updateUI();
                        }
                    }


                } else if (response.body().getStatus() == 0) {
                    Toast.makeText(mContext, "Meeting not Deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllBookingsApiResponse> call, Throwable t) {
                Log.i("API RESPONSE FAIL", t.toString());
                networkIssue();
            }
        });

    }

    public void networkIssue() {
        progress.dismiss();
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(mContext);
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

    public void updateUI() {
        //    listOfAllDataOfRoomDetail = response.body().getData();
        MyBookingCalenderFragment.mSavedEventsPerDay = new ArrayList<HashMap<String, Integer>>();

        MyBookingCalenderFragment.mSavedEventDays = new ArrayList<Integer>();

        for (int i = 0; i < MyBookingCalenderFragment.listOfAllDataOfRoomDetail.size(); i++) {

            int eventPerDay = 0;

            for (int j = 0; j < MyBookingCalenderFragment.listOfAllDataOfRoomDetail.size(); j++) {
                if (MyBookingCalenderFragment.listOfAllDataOfRoomDetail.get(i).getBookingdate().equals(MyBookingCalenderFragment.listOfAllDataOfRoomDetail.get(j).getBookingdate())) {
                    eventPerDay = eventPerDay + 1;
                }
            }

            int day = Integer.parseInt(MyBookingCalenderFragment.listOfAllDataOfRoomDetail.get(i).getBookingdate().substring(MyBookingCalenderFragment.listOfAllDataOfRoomDetail.get(i).getBookingdate().length() - 2));
            HashMap<String, Integer> dayInfo = new HashMap<String, Integer>();
            dayInfo.put("day" + day, eventPerDay);
            MyBookingCalenderFragment.mSavedEventDays.add(day);
            MyBookingCalenderFragment.mSavedEventsPerDay.add(dayInfo);
            Log.d("EVENTS_PER DAY", String.valueOf(dayInfo));

        }

        MyBookingCalenderFragment.getSelectedDateData(MyBookingCalender.selectedDate);

        Log.d("SAVED_EVENT_DATES", String.valueOf(MyBookingCalenderFragment.mSavedEventDays));
        MyBookingCalenderFragment.mMaterialCalendarAdapter.notifyDataSetChanged();

        MyBookingCalenderFragment.mSavedEventsAdapter.notifyDataSetChanged();

    }

    private static class ViewHolder {
        TextView eventTitle, organiser, venueText, startTimeText, endTimeText, setBookingId;
        View mDivider;
        ImageView deleteImage;

    }

}





