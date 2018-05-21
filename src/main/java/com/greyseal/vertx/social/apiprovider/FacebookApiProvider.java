package com.greyseal.vertx.social.apiprovider;

import com.greyseal.vertx.boot.Constant.MediaType;
import com.greyseal.vertx.social.helper.ApiProviderConfigHelper;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;

@ApiProvider(name = ApiProviderName.FACEBOOK, type = ApiProviderType.OAUTH2)
public class FacebookApiProvider extends AbstractApiProvider {
    //https://wp-native-articles.com/blog/news/how-to-fix-facebook-apps-error-cant-load-url-domain-url-isnt-included-apps-domains/
    private static final String APP_ID = ApiProviderConfigHelper.getAppId("facebook");
    private static final String APP_SECRET = ApiProviderConfigHelper.getAppSecret("facebook");
    private static final String BASE_URL = ApiProviderConfigHelper.getBaseURL("facebook");
    private static final String BASIC_PROFILE_URL = String.join("/", BASE_URL, "me");

    public FacebookApiProvider(final Vertx vertx) {
        super(vertx, APP_ID, APP_SECRET, ApiProviderName.FACEBOOK);
    }

    @Override
    public Future<JsonObject> getOAuth2AccessToken(final JsonObject params) {
        final JsonObject _params = params.copy();
        return super.getOAuth2AccessToken(_params);
    }

    @Override
    public Future<JsonObject> doPost(JsonObject payload) {
        return null;
    }

    public Future<JsonObject> doGetProfile(final JsonObject data) {
        final JsonObject _data = data.copy();
        return super.doGetProfile(getAccessToken(_data), BASIC_PROFILE_URL, getHeaders());
    }

    protected JsonObject getHeaders() {
        JsonObject headers = new JsonObject();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.put("x-li-format", "json");
        return headers;
    }
}