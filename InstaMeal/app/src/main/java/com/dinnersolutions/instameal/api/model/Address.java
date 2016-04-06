package com.dinnersolutions.instameal.api.model;

import java.util.UUID;

public class Address {
    private UUID id;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private GeoPoint coordinates;
}
