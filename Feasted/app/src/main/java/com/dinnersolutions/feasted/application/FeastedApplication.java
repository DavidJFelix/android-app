package com.dinnersolutions.feasted.application;

import android.app.Application;
import android.content.Context;

import com.dinnersolutions.feasted.dagger.ApiModule;
import com.dinnersolutions.feasted.dagger.DaggerFeastedComponent;
import com.dinnersolutions.feasted.dagger.DataModule;
import com.dinnersolutions.feasted.dagger.FeastedComponent;

/**
 * Created by Dejan Ristic on 1/10/16.
 */
public class FeastedApplication extends Application {

    private boolean isProduction = true;
    private FeastedComponent feastedComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        feastedComponent = DaggerFeastedComponent.builder()
                .dataModule(new DataModule())
                .apiModule(new ApiModule(this, isProduction))
                .build();
        feastedComponent.inject(this);
    }

    public static FeastedApplication from(Context context) {
        return (FeastedApplication) context.getApplicationContext();
    }

    public FeastedComponent component() {
        return feastedComponent;
    }
}
