package com.example.roombooking.roombooking.landing;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roombooking.roombooking.sharedPreferences.AppPreferences;
import com.example.roombooking.roombooking.sharedPreferences.MRBSApplication;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class MaterialCalendar {
    // Variables
    protected static int mMonth = -1;
    protected static int mYear = -1;
    protected static int mCurrentDay = -1;
    protected static int mCurrentMonth = -1;
    protected static int mCurrentYear = -1;
    protected static int mFirstDay = -1;
    protected static int mNumDaysInMonth = -1;
    public static String selectedDate;
    static AppPreferences preference;


    protected static void getInitialCalendarInfo(Context context) {

        int month = Integer.parseInt(MRBSApplication.getPref().getString(preference.month));
        int year = Integer.parseInt(MRBSApplication.getPref().getString(preference.year));
        int day = Integer.parseInt(MRBSApplication.getPref().getString(preference.day));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.DAY_OF_MONTH,day);

        if (cal != null) {
            mNumDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            mMonth = cal.get(Calendar.MONTH);
            mYear = cal.get(Calendar.YEAR);
            mCurrentDay = cal.get(Calendar.DAY_OF_MONTH);
            mCurrentMonth = mMonth;
            mCurrentYear = mYear;

            getFirstDay(mMonth, mYear);

        }

    }

    private static void refreshCalendar(TextView monthTextView, GridView calendarGridView,
                                        MaterialCalendarAdapter materialCalendarAdapter, int month, int year) {

        Log.i("MONTH",month+"");
        Log.i("YEAR",year+"");

        checkCurrentDay(month, year);
        getNumDayInMonth(month, year);
        getFirstDay(month, year);

        if (monthTextView != null) {
            monthTextView.setText(getMonthName(month) + " " + year);
        }

        // Clear Saved Events ListView count when changing calendars
        if (MaterialCalendarFragment.mSavedEventsAdapter != null) {
            MaterialCalendarFragment.mNumEventsOnDay = -1;
            MaterialCalendarFragment.mSavedEventsAdapter.notifyDataSetChanged();
        }

        MaterialCalendarFragment.getSavedEventsForCurrentMonth(mMonth,mYear);

        if (materialCalendarAdapter != null) {
            if (calendarGridView != null) {
                calendarGridView.setItemChecked(calendarGridView.getCheckedItemPosition(), false);
            }

            materialCalendarAdapter.notifyDataSetChanged();
        }
    }

    private static String getMonthName(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    private static void checkCurrentDay(int month, int year) {
        if (month == mCurrentMonth && year == mCurrentYear) {
            Calendar cal = Calendar.getInstance();
            mCurrentDay = cal.get(Calendar.DAY_OF_MONTH);
        } else {
            mCurrentDay = -1;
        }
    }

    private static void getNumDayInMonth(int month, int year) {
        Calendar cal = Calendar.getInstance();
        if (cal != null) {
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            mNumDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }

    private static void getFirstDay(int month, int year) {
        Calendar cal = Calendar.getInstance();
        if (cal != null) {
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.DAY_OF_MONTH, 1);

            switch (cal.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    mFirstDay = 0;
                    break;

                case Calendar.MONDAY:
                    mFirstDay = 1;
                    break;

                case Calendar.TUESDAY:
                    mFirstDay = 2;
                    break;

                case Calendar.WEDNESDAY:
                    mFirstDay = 3;
                    break;

                case Calendar.THURSDAY:
                    mFirstDay = 4;
                    break;

                case Calendar.FRIDAY:
                    mFirstDay = 5;
                    break;

                case Calendar.SATURDAY:
                    mFirstDay = 6;
                    break;

                default:
                    break;
            }
        }
    }

    // Call in View.OnClickListener for Previous ImageView
    protected static void previousOnClick(ImageView previousImageView, TextView monthTextView,
                                          GridView calendarGridView, MaterialCalendarAdapter materialCalendarAdapter) {
        if (previousImageView != null && mMonth != -1 && mYear != -1) {
            previousMonth(monthTextView, calendarGridView, materialCalendarAdapter);
        }
    }

    // Call in View.OnClickListener for Next ImageView
    protected static void nextOnClick(ImageView nextImageView, TextView monthTextView,
                                      GridView calendarGridView,
                                      MaterialCalendarAdapter materialCalendarAdapter) {
        if (nextImageView != null && mMonth != -1 && mYear != -1) {
            nextMonth(monthTextView, calendarGridView, materialCalendarAdapter);
        }
    }

    private static void previousMonth(TextView monthTextView, GridView calendarGridView,
                                      MaterialCalendarAdapter materialCalendarAdapter) {
        if (mMonth == 0) {
            mMonth = 11;
            mYear = mYear - 1;
        } else {
            mMonth = mMonth - 1;
        }

        refreshCalendar(monthTextView, calendarGridView, materialCalendarAdapter, mMonth, mYear);
    }

    private static void nextMonth(TextView monthTextView, GridView calendarGridView,
                                  MaterialCalendarAdapter materialCalendarAdapter) {
        if (mMonth == 11) {
            mMonth = 0;
            mYear = mYear + 1;
        } else {
            mMonth = mMonth + 1;
        }

        refreshCalendar(monthTextView, calendarGridView, materialCalendarAdapter, mMonth, mYear);
    }

    // Call in GridView.OnItemClickListener for custom Calendar GirdView
    protected static void selectCalendarDay(MaterialCalendarAdapter materialCalendarAdapter2, int position) {
    //    Log.d("SELECTED_POSITION", String.valueOf(position));
        int weekPositions = 6;
        int noneSelectablePositions = weekPositions + mFirstDay;

        if (position > noneSelectablePositions) {
            getSelectedDate(position, mMonth, mYear);

            if (materialCalendarAdapter2 != null) {
                materialCalendarAdapter2.notifyDataSetChanged();
            }
        }
    }

    private static void getSelectedDate(int selectedPosition, int month, int year) {
        int weekPositions = 6;
          month++;

        int dateNumber = selectedPosition - weekPositions - mFirstDay;
      //  Log.d("DATE_NUMBER", String.valueOf(dateNumber));

           selectedDate=year+"-"+month+"-"+dateNumber;

      //  Log.d("SELECTED_DATE", String.valueOf(month + "/" + dateNumber + "/" + year));
    }

}

