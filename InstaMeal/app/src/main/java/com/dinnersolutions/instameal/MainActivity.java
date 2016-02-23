package com.dinnersolutions.instameal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dinnersolutions.instameal.api.AuthManager;
import com.dinnersolutions.instameal.api.FeastedApi;
import com.dinnersolutions.instameal.application.FeastedApplication;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Inject
    FeastedApi feastedApi;
    @Inject
    AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inject(this);
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        if (!authManager.isAuthenticated()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void inject(Context context) {
        FeastedApplication app = FeastedApplication.from(context);
        app.component().inject(this);
    }
}
