package com.greyseal.vertx.social.helper;

import com.greyseal.vertx.boot.helper.ConfigHelper;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class ApiProviderConfigHelper {
    private static final JsonArray apiProviders = ConfigHelper.loadConfigurationByEnvironment().getJsonArray("api_providers", new JsonArray());

    public static JsonObject get(final String providerName) {
        return apiProviders.getJsonObject(0).getJsonObject(providerName);
    }

    public static String getClientId(final String providerName) {
        return get(providerName).getString("client_id");
    }

    public static String getClientSecret(final String providerName) {
        return get(providerName).getString("client_secret");
    }
}
