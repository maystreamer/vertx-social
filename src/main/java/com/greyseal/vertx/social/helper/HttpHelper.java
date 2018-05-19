package com.greyseal.vertx.social.helper;

import com.greyseal.vertx.boot.helper.ConfigHelper;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;

public class HttpHelper {
    private static final HttpClientOptions options = new HttpClientOptions(ConfigHelper.loadConfigurationByEnvironment().getJsonObject("http_client_options", new JsonObject()));

    public static HttpClientOptions getHttpClientOptions() {
        return options;
    }
}