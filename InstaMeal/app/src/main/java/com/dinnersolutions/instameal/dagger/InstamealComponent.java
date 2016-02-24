package com.dinnersolutions.instameal.dagger;

import com.dinnersolutions.instameal.LoginActivity;
import com.dinnersolutions.instameal.MainActivity;
import com.dinnersolutions.instameal.api.AuthManager;
import com.dinnersolutions.instameal.application.InstamealApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
@Singleton
@Component(modules = {ApiModule.class, DataModule.class})
public interface InstamealComponent {

    void inject(InstamealApplication app);

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(AuthManager manager);

}
