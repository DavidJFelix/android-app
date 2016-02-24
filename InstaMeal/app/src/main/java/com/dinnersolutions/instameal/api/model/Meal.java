package com.dinnersolutions.instameal.api.model;

import java.util.List;

/**
 * Created by Dejan Ristic on 2/23/16.
 */
public class Meal {

    public String name;
    public String mealId;
    public String imageUrl;
    public String description;
    public String availableTo;
    public String availableFrom;

    public float price;
    public int portions;

    public boolean isActive;

    public List<Ingredient> ingredients;
    public List<Allergen> allergens;

    public MealLocation location;

}
