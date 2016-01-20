package com.dinnersolutions.feasted.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.dinnersolutions.feasted.api.model.User;
import com.dinnersolutions.feasted.application.FeastedApplication;

import javax.inject.Inject;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
public class AuthManager {

    @Inject
    SharedPreferences prefs;

    public static final String BEARER_TOKEN = "bearer_token";

    public User activeUser;

    public AuthManager(Context context) {
        inject(context);
    }

    public void saveBearerToken(String bearerToken) {
        String tokenString = "Bearer " + bearerToken;
        prefs.edit().putString(BEARER_TOKEN, tokenString).apply();
    }

    public String getBearerToken() {
        return prefs.getString(BEARER_TOKEN, "");
    }

    public void unauthenticateActiveUser() {
        activeUser = null;
        prefs.edit().remove(BEARER_TOKEN).apply();
    }


    public void inject(Context context) {
        FeastedApplication app = FeastedApplication.from(context);
        app.component().inject(this);
    }
}
