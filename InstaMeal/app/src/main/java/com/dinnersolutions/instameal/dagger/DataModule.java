package com.dinnersolutions.instameal.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dinnersolutions.instameal.api.AuthManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
@Module(includes = ApiModule.class)
public class DataModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    AuthManager provideAuthManager(Context context, SharedPreferences prefs) {
        return new AuthManager(context, prefs);
    }
}
