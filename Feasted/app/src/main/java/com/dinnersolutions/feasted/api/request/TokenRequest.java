package com.dinnersolutions.feasted.api.request;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
public class TokenRequest {
    public String token;

    public TokenRequest() {
        //no arg
    }

    public TokenRequest(String token) {
        this.token = token;
    }

}
