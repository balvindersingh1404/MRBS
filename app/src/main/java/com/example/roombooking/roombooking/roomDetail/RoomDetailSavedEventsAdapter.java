package com.example.roombooking.roombooking.roomDetail;

/**
 * Created by balvinder on 29/5/17.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.models.AllBookingsApiResponse;
import com.example.roombooking.roombooking.models.AllBookingsData;

import java.util.List;

import retrofit2.Call;

public class RoomDetailSavedEventsAdapter extends BaseAdapter {
    private static RoomDetailSavedEventsAdapter.ViewHolder mHolder;
    List<AllBookingsData> selectDateData;
    private Context mContext;
    private Call<AllBookingsApiResponse> call;

    // Constructor
    public RoomDetailSavedEventsAdapter(Context context, List<AllBookingsData> selectDateData) {

        mContext = context;
        this.selectDateData = selectDateData;

    }

    @Override
    public int getCount() {
        if (RoomDetailCalenderFragment.mNumEventsOnDay != 0 || RoomDetailCalenderFragment.mNumEventsOnDay != -1) {
            return RoomDetailCalenderFragment.mNumEventsOnDay;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (selectDateData != null && position < selectDateData.size()) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_material_saved_event_item, parent, false);

                mHolder = new RoomDetailSavedEventsAdapter.ViewHolder();

                if (convertView != null) {

                    TextView eventTitle, organiser, venueText, startTimeText, endTimeText;

                    mHolder.eventTitle = (TextView) convertView.findViewById(R.id.eventTitle);
                    mHolder.organiser = (TextView) convertView.findViewById(R.id.organiser);
                    mHolder.venueText = (TextView) convertView.findViewById(R.id.venueText);
                    mHolder.startTimeText = (TextView) convertView.findViewById(R.id.startTimeText);
                    mHolder.endTimeText = (TextView) convertView.findViewById(R.id.endTimeText);
                    mHolder.setBookingId = (TextView) convertView.findViewById(R.id.setBookingId);

                    convertView.setTag(mHolder);
                }
            } else {
                mHolder = (RoomDetailSavedEventsAdapter.ViewHolder) convertView.getTag();
            }

            // Animates in each cell when added to the ListView
            Animation animateIn = AnimationUtils.loadAnimation(mContext, R.anim.listview_top_down);
            if (convertView != null && animateIn != null) {
                convertView.startAnimation(animateIn);
            }
            if (mHolder.eventTitle != null) {
                mHolder.eventTitle.setText(this.selectDateData.get(position).getTitle());
                if(this.selectDateData.get(position).getMeetingtype().equals("EXTERNAL"))
                    convertView.setBackgroundColor(mContext.getResources().getColor(R.color.external));
                else
                    convertView.setBackgroundColor(mContext.getResources().getColor(R.color.internal));
            }
            if (mHolder.organiser != null) {
                mHolder.organiser.setText(this.selectDateData.get(position).getUsername());
            }
            if (mHolder.venueText != null) {
                mHolder.venueText.setText(this.selectDateData.get(position).getRoomname());
            }
            if (mHolder.startTimeText != null) {
                mHolder.startTimeText.setText(this.selectDateData.get(position).getStarttime());
            }
            if (mHolder.endTimeText != null) {
                mHolder.endTimeText.setText(this.selectDateData.get(position).getEndtime());
            }
            if (mHolder.setBookingId != null) {
                mHolder.setBookingId.setText(this.selectDateData.get(position).getBookingid());
            }
        }

        return convertView;
    }


    private static class ViewHolder {
        TextView eventTitle, organiser, venueText, startTimeText, endTimeText,setBookingId;
        View mDivider;

    }

}





