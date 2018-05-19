package com.greyseal.vertx.social.apiprovider;

public enum ApiProviderType {
    OAUTH1("OAuth1"), OAUTH2("OAuth2"), TOKEN("Token");

    private String type;

    ApiProviderType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
