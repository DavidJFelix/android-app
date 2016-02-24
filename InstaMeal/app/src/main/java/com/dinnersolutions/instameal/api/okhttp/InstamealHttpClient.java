package com.dinnersolutions.instameal.api.okhttp;

import android.content.Context;
import android.content.pm.PackageManager;

import com.dinnersolutions.instameal.api.AuthManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
public class InstamealHttpClient extends OkHttpClient {

    public InstamealHttpClient(Context context, AuthManager authManager, boolean isProduction) {

        interceptors().add(chain -> {

            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();

            String userAgent = "Feasted Android";
            String versionName = "";
            try {
                versionName = context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0).versionName;
                userAgent += " " + versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            requestBuilder.header("Accept", "application/json")
                    .header("User-Agent", userAgent)
                    .header("Authorization", authManager.getBearerToken());

            return chain.proceed(requestBuilder.build());
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        interceptors().add(interceptor);
    }
}

