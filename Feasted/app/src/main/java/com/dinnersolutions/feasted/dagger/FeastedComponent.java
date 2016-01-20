package com.dinnersolutions.feasted.dagger;

import android.support.v7.app.AppCompatActivity;

import com.dinnersolutions.feasted.api.AuthManager;
import com.dinnersolutions.feasted.application.FeastedApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
@Singleton
@Component(modules = {ApiModule.class, DataModule.class})
public interface FeastedComponent {

    void inject(FeastedApplication app);

    void inject(AppCompatActivity activity);

    void inject(AuthManager manager);

}
