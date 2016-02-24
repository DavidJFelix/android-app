package com.dinnersolutions.instameal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinnersolutions.instameal.api.model.Meal;

import java.util.List;

/**
 * Created by Dejan Ristic on 2/23/16.
 */
public class MealAdapter extends
        RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> meals;

    public MealAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    @Override
    public MealAdapter.MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(MealAdapter.MealViewHolder viewHolder, int position) {
        Meal contact = meals.get(position);

    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        public MealViewHolder(View itemView) {
            super(itemView);
        }
    }
}