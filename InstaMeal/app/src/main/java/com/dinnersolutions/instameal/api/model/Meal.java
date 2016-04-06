package com.dinnersolutions.instameal.api.model;

import java.util.List;
import java.util.UUID;

public class Meal {
    private UUID id;
    private String name;
    private String description;
    private List<Allergen> allergens;
    private List<DietaryFilter> dietaryFilters;
    private List<Ingredient> ingredients;
    private String availableTo;
    private String availableFrom;

    private Price price;
    private int portions;

    public boolean isActive;


}
