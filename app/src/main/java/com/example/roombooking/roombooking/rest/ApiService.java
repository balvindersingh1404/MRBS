package com.example.roombooking.roombooking.rest;

import com.example.roombooking.roombooking.models.AllBookingsData;
import com.example.roombooking.roombooking.roomList.Example;
import com.example.roombooking.roombooking.models.AllBookingsApiResponse;
import com.example.roombooking.roombooking.models.AllBookingsRequest;
import com.example.roombooking.roombooking.models.ApiResponse;
import com.example.roombooking.roombooking.models.AvailableRoomsRequest;
import com.example.roombooking.roombooking.models.BookingByUserIdRequest;
import com.example.roombooking.roombooking.models.ConfirmBookingApiresponse;
import com.example.roombooking.roombooking.models.LoginDetails;
import com.example.roombooking.roombooking.models.RoomsAvailableApiResponse;
import com.example.roombooking.roombooking.models.SearchByDate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

//    @Multipart
//    @POST("login.php")
//    Call<ApiResponse> loginUser(@Part("emailid") RequestBody emailid, @Part("password") RequestBody password);

    @POST("login.php")
    Call<ApiResponse> loginUser(@Body LoginDetails loginDetails);

    @GET("getrooms.php")
    Call<Example> getRoomList();

    @POST("getavailablerooms.php")
    Call<RoomsAvailableApiResponse> getAvailableRooms(@ Body AvailableRoomsRequest availableRoomsDetail);


    @POST("newroombooking.php")
    Call<ConfirmBookingApiresponse> confirmBooking(@ Body AvailableRoomsRequest availableRoomsDetail);

    @POST("getroombookingformonth.php")
    Call<AllBookingsApiResponse> allBookings(@ Body AllBookingsRequest allBookingsRequest);

    @POST("getroombookingfordate.php")
    Call<AllBookingsApiResponse> searchByDate(@ Body SearchByDate searchByDate);

    @POST("getroombookingformonth.php")
    Call<AllBookingsApiResponse> bookingByUserId(@ Body BookingByUserIdRequest bookingByUserIdRequest);

    @POST("cancelmeeting.php")
    Call<AllBookingsApiResponse> deleteUserBooking(@ Body AllBookingsData allBookingsData);
}

