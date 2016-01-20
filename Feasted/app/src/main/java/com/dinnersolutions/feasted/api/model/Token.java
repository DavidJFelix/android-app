package com.dinnersolutions.feasted.api.model;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
public class Token {
    public String type;
    public String text;

    public Token() {
        // no arg
    }

    public String getFullToken() {
        return type + " " + text;
    }

}
