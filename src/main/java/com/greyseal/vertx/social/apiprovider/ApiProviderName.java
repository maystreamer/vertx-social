package com.greyseal.vertx.social.apiprovider;

public enum ApiProviderName {
    LINKEDIN("LinkedIn"), FACEBOOK("Facebook"), TWITTER("Twitter"), GITHUB("Github");

    private String type;

    ApiProviderName(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }


}