package com.greyseal.vertx.social.apiprovider;

import com.greyseal.vertx.boot.Constant.MediaType;
import com.greyseal.vertx.social.helper.ApiProviderConfigHelper;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;

@ApiProvider(name = ApiProviderName.GITHUB, type = ApiProviderType.OAUTH2)
public class GithubApiProvider extends AbstractApiProvider {
    private static final String CLIENT_ID = ApiProviderConfigHelper.getClientId("github");
    private static final String CLIENT_SECRET = ApiProviderConfigHelper.getClientSecret("github");
    private static final ApiProviderName API_PROVIDER_NAME = ApiProviderName.GITHUB;


    private static final String BASE_URL = "https://api.github.com";
    private static final String USER_PROFILE_URL = String.join("/", BASE_URL, "user");

    public GithubApiProvider(final Vertx vertx) {
        super(vertx, CLIENT_ID, CLIENT_SECRET, API_PROVIDER_NAME);
    }

    @Override
    public Future<JsonObject> doPost(JsonObject data) {
        return null;
    }

    @Override
    public Future<JsonObject> getOAuth2AccessToken(final JsonObject params) {
        final JsonObject _params = params.copy();
        return super.getOAuth2AccessToken(_params);
    }

    public Future<JsonObject> doGetProfile(final JsonObject accessToken) {
        final JsonObject _accessToken = accessToken.copy();
        return super.doGetProfile(_accessToken.getString("access_token"), USER_PROFILE_URL, getHeaders());
    }

    protected JsonObject getHeaders() {
        JsonObject headers = new JsonObject();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.put("Accept", " application/vnd.github.v3.text-match+json");
        return headers;
    }
}
