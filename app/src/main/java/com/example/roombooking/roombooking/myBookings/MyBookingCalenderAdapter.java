package com.example.roombooking.roombooking.myBookings;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roombooking.roombooking.R;


public class MyBookingCalenderAdapter extends BaseAdapter {
    private static MyBookingCalenderAdapter.ViewHolder mHolder;
    int mWeekDayNames = 7;
    int mGridViewIndexOffset = 1;
    // Variables
    private Context mContext;

    // Constructor
    public MyBookingCalenderAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        if (MyBookingCalender.mFirstDay != -1 && MyBookingCalender.mNumDaysInMonth != -1) {
            Log.d("GRID_COUNT", String.valueOf(mWeekDayNames + MyBookingCalender.mFirstDay + MyBookingCalender.mNumDaysInMonth));
            return mWeekDayNames + MyBookingCalender.mFirstDay + MyBookingCalender.mNumDaysInMonth;
        }

        return mWeekDayNames;
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_material_day, parent, false);

            mHolder = new MyBookingCalenderAdapter.ViewHolder();

            if (convertView != null) {
                mHolder.mSelectedDayImageView = (ImageView) convertView.findViewById(R.id.material_calendar_selected_day);
                mHolder.mTextView = (TextView) convertView.findViewById(R.id.material_calendar_day);
                mHolder.mSavedEventImageView = (ImageView) convertView.findViewById(R.id.saved_event_imageView);
                convertView.setTag(mHolder);
            }
        } else {
            mHolder = (MyBookingCalenderAdapter.ViewHolder) convertView.getTag();
        }

        if (mHolder.mSelectedDayImageView != null) {
            GridView gridView = (GridView) parent;
            Log.d("ITEM_CHECKED_POSITION", String.valueOf(gridView.isItemChecked(position)));
            if (gridView.isItemChecked(position)) {
                Animation feedBackAnimation = AnimationUtils.loadAnimation(mContext, R.anim.selected_day_feedback);
                mHolder.mSelectedDayImageView.setVisibility(View.VISIBLE);

                if (feedBackAnimation != null) {
                    mHolder.mSelectedDayImageView.startAnimation(feedBackAnimation);
                }
            } else {
                mHolder.mSelectedDayImageView.setVisibility(View.INVISIBLE);
            }
        }


        if (mHolder.mTextView != null) {
            setCalendarDay(position);
        }

        if (mHolder.mSavedEventImageView != null) {
            setSavedEvent(position);
        }

        return convertView;
    }

    private void setCalendarDay(int position) {
        if (position <= mWeekDayNames - mGridViewIndexOffset + MyBookingCalender.mFirstDay) {
            mHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.calendar_day_text_color));
            Log.d("NO_CLICK_POSITION", String.valueOf(position));
        } else {
            mHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.calendar_number_text_color));
        }

        switch (position) {
            case 0:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.sunday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 1:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.monday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 2:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.tuesday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 3:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.wednesday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 4:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.thursday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 5:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.friday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            case 6:
                mHolder.mTextView.setText(mContext.getResources().getString(R.string.saturday));
                mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                break;

            default:
                Log.d("CURRENT_POSITION", String.valueOf(position));
                if (position < mWeekDayNames + MyBookingCalender.mFirstDay) {
                    Log.d("BLANK_POSITION", "This is a blank day");
                    mHolder.mTextView.setText("");
                    mHolder.mTextView.setTypeface(Typeface.DEFAULT);
                } else {
                    mHolder.mTextView.setText(String.valueOf(position - (mWeekDayNames - mGridViewIndexOffset) -
                            MyBookingCalender.mFirstDay));
                    mHolder.mTextView.setTypeface(Typeface.DEFAULT_BOLD);

                    if (MyBookingCalender.mCurrentDay != -1) {
                        int startingPosition = mWeekDayNames - mGridViewIndexOffset + MyBookingCalender.mFirstDay;
                        int currentDayPosition = startingPosition +MyBookingCalender.mCurrentDay;

                        if (position == currentDayPosition) {
                            mHolder.mTextView.setTextColor(mContext.getResources().getColor(
                                    R.color.calendar_current_number_text_color));
                        } else {
                            mHolder.mTextView.setTextColor(mContext.getResources().getColor(
                                    R.color.calendar_number_text_color));
                        }
                    } else {
                        mHolder.mTextView.setTextColor(mContext.getResources().getColor(
                                R.color.calendar_number_text_color));
                    }
                }
                break;
        }
    }

    @SuppressLint("LongLogTag")
    private void setSavedEvent(int position) {
        // Reset saved position indicator
        mHolder.mSavedEventImageView.setVisibility(View.INVISIBLE);

        if (MyBookingCalender.mFirstDay != -1 && MyBookingCalenderFragment.mSavedEventDays != null &&
                MyBookingCalenderFragment.mSavedEventDays.size() > 0) {

            int startingPosition = mWeekDayNames - mGridViewIndexOffset + MyBookingCalender.mFirstDay;
            Log.d("SAVED_EVENT_STARTING_POS", String.valueOf(startingPosition));
            if (position > startingPosition) {
                for (int i = 0; i < MyBookingCalenderFragment.mSavedEventDays.size(); i++) {
                    int savedEventPosition = startingPosition + MyBookingCalenderFragment.mSavedEventDays.get(i);

                    Log.d("POSITION", String.valueOf(position));
                    Log.d("SAVED_POSITION", String.valueOf(savedEventPosition));
                    if (position == savedEventPosition) {
                        mHolder.mSavedEventImageView.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                mHolder.mSavedEventImageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private static class ViewHolder {
        ImageView mSelectedDayImageView;
        TextView mTextView;
        ImageView mSavedEventImageView;
    }
}
