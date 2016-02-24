package com.dinnersolutions.instameal.api.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Dejan Ristic on 2/23/16.
 */
public class MealLocation {

    public String type;
    public Coordinates coordinates;

    public LatLng getLatLng() {
        return new LatLng(coordinates.lat, coordinates.lng);
    }
}
