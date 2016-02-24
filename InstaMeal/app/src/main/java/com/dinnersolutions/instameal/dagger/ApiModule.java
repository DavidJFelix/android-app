package com.dinnersolutions.instameal.dagger;

import android.content.Context;

import com.dinnersolutions.instameal.api.AuthManager;
import com.dinnersolutions.instameal.api.InstamealApi;
import com.dinnersolutions.instameal.api.InstamealService;
import com.dinnersolutions.instameal.api.okhttp.InstamealHttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Dejan Ristic on 1/17/16.
 */

@Module
public class ApiModule {

    public static final String SERVER = "http://api.davidjfelix.com";
    public static final String STAGE_SERVER = "https://dev";

    private boolean isProduction;
    private Context context;

    public ApiModule(Context context, boolean isProduction) {
        this.context = context;
        this.isProduction = isProduction;
    }

    @Provides
    boolean provideIsProduction() {
        return isProduction;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    String provideEndpoint(boolean isProduction) {
        if (isProduction) {
            return SERVER;
        } else {
            return STAGE_SERVER;
        }
    }

    @Provides
    @Singleton
    InstamealHttpClient provideHttpClient(Context context, AuthManager authManager, boolean isProduction) {
        return new InstamealHttpClient(context, authManager, isProduction);
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    InstamealService provideTripService(InstamealHttpClient feastedHttpClient, Gson gson, String endpoint) {
        return new Retrofit.Builder()
                .client(feastedHttpClient)
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(InstamealService.class);
    }

    @Provides
    @Singleton
    InstamealApi provideFeastedApi(InstamealService service, AuthManager manager) {
        return new InstamealApi(service, manager);
    }

}
