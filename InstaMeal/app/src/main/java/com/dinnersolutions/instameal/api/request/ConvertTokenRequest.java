package com.dinnersolutions.instameal.api.request;

public class ConvertTokenRequest {
    private static final String grantType = "convert_token";
    // The oauth provider who we are exchanging tokens with
    private String backend;
    // The app's client id with instameals server -- issued by the server, pre-compile time
    private String client_id;
    // The app's client secret with instameals server -- issued by the server, pre-compile time
    // I'm not clear on which methods you need this for or how secret is should be
    private String client_secret;
    // The token issued by the oauth provider
    private String token;

    public ConvertTokenRequest(
            String backend, String token, String client_id, String client_secret) {
        this.backend = backend;
        this.token = token;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public static ConvertTokenRequest convertFacebook(
            String token, String client_id, String client_secret) {
        return new ConvertTokenRequest("facebook", token, client_id, client_secret);
    }

    public static ConvertTokenRequest convertGoogle(
            String token, String client_id, String client_secret) {
        return new ConvertTokenRequest("google", token, client_id, client_secret);
    }
}
