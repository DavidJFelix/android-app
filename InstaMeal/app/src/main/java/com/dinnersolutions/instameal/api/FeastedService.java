package com.dinnersolutions.instameal.api;

import com.dinnersolutions.instameal.api.request.TokenRequest;
import com.dinnersolutions.instameal.api.response.TokenUserResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
public interface FeastedService {

    @POST("/user/by-token")
    Call<TokenUserResponse> getTokenAndUser(@Body TokenRequest request);
}
