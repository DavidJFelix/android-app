package com.dinnersolutions.instameal.api;

import com.dinnersolutions.instameal.api.request.TokenRequest;
import com.dinnersolutions.instameal.api.response.MealResponse;
import com.dinnersolutions.instameal.api.response.TokenUserResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
public interface InstamealService {

    @POST("/user/by-token")
    Call<TokenUserResponse> getTokenAndUser(@Body TokenRequest request);

    @GET("/meals")
    Call<MealResponse> getMeals(@Query("lat") double lat, @Query("lng") double lng, @Query("radius") int radius);
}
