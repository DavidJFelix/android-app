package com.dinnersolutions.instameal.api;

import com.dinnersolutions.instameal.api.request.TokenRequest;
import com.dinnersolutions.instameal.api.response.TokenUserResponse;

import retrofit.Call;
import retrofit.Callback;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
public class FeastedApi {

    private FeastedService service;
    private AuthManager authManager;

    public FeastedApi(FeastedService service, AuthManager authManager) {
        this.service = service;
        this.authManager = authManager;
    }

    public void getTokenAndUser(Callback<TokenUserResponse> responseCallback) {
        TokenRequest tokenRequest = new TokenRequest(authManager.getBearerToken());
        Call<TokenUserResponse> tokenUserResponseCall = service.getTokenAndUser(tokenRequest);
        tokenUserResponseCall.enqueue(responseCallback);
    }

}
