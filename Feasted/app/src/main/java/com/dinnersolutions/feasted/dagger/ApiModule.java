package com.dinnersolutions.feasted.dagger;

import android.content.Context;

import com.dinnersolutions.feasted.api.AuthManager;
import com.dinnersolutions.feasted.api.FeastedApi;
import com.dinnersolutions.feasted.api.okhttp.FeastedHttpClient;
import com.dinnersolutions.feasted.api.FeastedService;
import com.google.gson.FieldNamingPolicy;
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
    FeastedHttpClient provideHttpClient(Context context, AuthManager authManager, boolean isProduction) {
        return new FeastedHttpClient(context, authManager, isProduction);
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    FeastedService provideTripService(FeastedHttpClient feastedHttpClient, Gson gson, String endpoint) {
        return new Retrofit.Builder()
                .client(feastedHttpClient)
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(FeastedService.class);
    }

    @Provides
    @Singleton
    FeastedApi provideFeastedApi(FeastedService service, AuthManager manager) {
        return new FeastedApi(service, manager);
    }

}
