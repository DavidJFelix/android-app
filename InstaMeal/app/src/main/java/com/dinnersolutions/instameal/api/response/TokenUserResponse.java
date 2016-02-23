package com.dinnersolutions.instameal.api.response;

import com.dinnersolutions.instameal.api.model.Token;
import com.dinnersolutions.instameal.api.model.User;

/**
 * Created by Dejan Ristic on 1/19/16.
 */
public class TokenUserResponse {
    public User user;
    public Token token;

    public TokenUserResponse() {
        //no arg
    }
}
