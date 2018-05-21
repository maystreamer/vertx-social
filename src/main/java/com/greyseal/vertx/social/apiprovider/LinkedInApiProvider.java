package com.greyseal.vertx.social.apiprovider;

import com.greyseal.vertx.boot.Constant.MediaType;
import com.greyseal.vertx.social.helper.ApiProviderConfigHelper;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;

@ApiProvider(name = ApiProviderName.LINKEDIN, type = ApiProviderType.OAUTH2)
public class LinkedInApiProvider extends AbstractApiProvider {
    private static final String CLIENT_ID = ApiProviderConfigHelper.getClientId("linkedin");
    private static final String CLIENT_SECRET = ApiProviderConfigHelper.getClientSecret("linkedin");
    private static final ApiProviderName API_PROVIDER_NAME = ApiProviderName.LINKEDIN;

    private static final String BASE_URL = "https://api.linkedin.com/v1";
    private static final String BASIC_PROFILE_URL = String.join("/", BASE_URL, "people/~?format=json");
    private static final String COMPANY_SHARE_URL = String.join("/", BASE_URL, "companies/:id/shares?format=json");
    private static final String PEOPLE_SHARE_URL = String.join("/", BASE_URL, "people/~/shares?format=json");

    public LinkedInApiProvider(final Vertx vertx) {
        super(vertx, CLIENT_ID, CLIENT_SECRET, API_PROVIDER_NAME);
    }

    @Override
    public Future<JsonObject> doPost(final JsonObject payload) {
        final JsonObject _params = payload.copy();
        long companyId = _params.getLong("company_id");
        String _url = null;
        if (companyId > 0) {
            _url = COMPANY_SHARE_URL;
        } else {
            _url = PEOPLE_SHARE_URL;
        }
        final String accessToken = getAccessToken(_params);
        _params.remove("access_token");
        _params.remove("company_id");
        return super.doPost(accessToken, _url, getHeaders(), _params);
    }

    @Override
    public Future<JsonObject> getOAuth2AccessToken(final JsonObject params) {
        final JsonObject _params = params.copy();
        return super.getOAuth2AccessToken(_params);
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