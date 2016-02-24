package com.dinnersolutions.instameal.api;

import com.dinnersolutions.instameal.api.model.Meal;
import com.dinnersolutions.instameal.api.request.TokenRequest;
import com.dinnersolutions.instameal.api.response.TokenUserResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
public class InstamealApi {

    private InstamealService service;
    private AuthManager authManager;

    public InstamealApi(InstamealService service, AuthManager authManager) {
        this.service = service;
        this.authManager = authManager;
    }

    public void getTokenAndUser(Callback<TokenUserResponse> responseCallback) {
        TokenRequest tokenRequest = new TokenRequest(authManager.getBearerToken());
        Call<TokenUserResponse> tokenUserResponseCall = service.getTokenAndUser(tokenRequest);
        tokenUserResponseCall.enqueue(responseCallback);
    }

    public void getMeals(LatLng latLng, int radius, Callback<List<Meal>> mealCallback) {
        Call<List<Meal>> mealCall = service.getMeals(latLng.latitude, latLng.longitude, radius);
        mealCall.enqueue(mealCallback);
    }

}
