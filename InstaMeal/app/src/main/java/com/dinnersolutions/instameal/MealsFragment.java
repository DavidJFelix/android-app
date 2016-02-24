package com.dinnersolutions.instameal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinnersolutions.instameal.api.InstamealApi;
import com.dinnersolutions.instameal.api.model.Meal;
import com.dinnersolutions.instameal.api.response.MealResponse;
import com.dinnersolutions.instameal.application.InstamealApplication;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class MealsFragment extends Fragment {

    @Inject
    InstamealApi instamealApi;

    @Bind(R.id.meals_list)
    RecyclerView mealList;

    private MealAdapter mealAdapter;

    public static MealsFragment newInstance() {
        return new MealsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initList();
        loadMeals();
    }

    private void initList() {
        mealAdapter = new MealAdapter(getActivity(), null);
        mealList.setLayoutManager(new LinearLayoutManager(getContext()));
        mealList.setHasFixedSize(true);
        mealList.setAdapter(mealAdapter);
    }

    private void loadMeals() {

        instamealApi.getMeals(new LatLng(39.15, -84.42), 10, new Callback<MealResponse>() {
            @Override
            public void onResponse(Response<MealResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    //todo npe

                    //todo replicate meal for show
                    List<Meal> expandedList = new ArrayList<Meal>();
                    for (int i = 0; i < 100; i++) {
                        expandedList.add(response.body().meals.get(0));
                    }

                    mealAdapter.updateMeals(expandedList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("MealsError", t.getMessage());
            }
        });
    }


    public void inject(Context context) {
        InstamealApplication app = InstamealApplication.from(context);
        app.component().inject(this);
    }
}
