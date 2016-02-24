package com.dinnersolutions.instameal.api;

import com.dinnersolutions.instameal.api.model.Meal;
import com.dinnersolutions.instameal.api.request.TokenRequest;
import com.dinnersolutions.instameal.api.response.TokenUserResponse;

import java.util.List;

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
    Call<List<Meal>> getMeals(@Query("lat") double lat, @Query("lng") double lng, @Query("radius") int radius);
}
