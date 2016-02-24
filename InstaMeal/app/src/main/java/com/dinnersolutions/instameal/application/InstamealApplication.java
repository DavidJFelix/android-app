package com.dinnersolutions.instameal.application;

import android.app.Application;
import android.content.Context;

import com.dinnersolutions.instameal.dagger.ApiModule;
import com.dinnersolutions.instameal.dagger.DaggerInstamealComponent;
import com.dinnersolutions.instameal.dagger.DataModule;
import com.dinnersolutions.instameal.dagger.InstamealComponent;

/**
 * Created by Dejan Ristic on 1/10/16.
 */
public class InstamealApplication extends Application {

    private boolean isProduction = true;
    private InstamealComponent instamealComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        instamealComponent = DaggerInstamealComponent.builder()
                .dataModule(new DataModule())
                .apiModule(new ApiModule(this, isProduction))
                .build();
        instamealComponent.inject(this);
    }

    public static InstamealApplication from(Context context) {
        return (InstamealApplication) context.getApplicationContext();
    }

    public InstamealComponent component() {
        return instamealComponent;
    }
}
