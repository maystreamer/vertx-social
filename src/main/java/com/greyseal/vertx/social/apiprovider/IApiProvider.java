package com.greyseal.vertx.social.apiprovider;

import com.greyseal.vertx.social.helper.HttpHelper;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.auth.oauth2.OAuth2Auth;
import org.apache.commons.lang3.NotImplementedException;

public interface IApiProvider {
    public static IApiProvider loadApiProvider(final String name, final Vertx vertx) throws ClassNotFoundException {
        final String apiProviderName = ApiProviderName.valueOf(name).type();
        return ApiProviderFactory.loadProvider(apiProviderName, vertx);
    }

    public static OAuth2Auth createOAuth2Auth(final Vertx vertx, final String clientId, final String clientSecret, final ApiProviderName type) {
        switch (type) {
            case LINKEDIN:
                return OAuth2Auth.newInstance(io.vertx.ext.auth.oauth2.providers.LinkedInAuth.create(vertx.getDelegate(), clientId, clientSecret, HttpHelper.getHttpClientOptions()));
            case GITHUB:
                return OAuth2Auth.newInstance(io.vertx.ext.auth.oauth2.providers.GithubAuth.create(vertx.getDelegate(), clientId, clientSecret, HttpHelper.getHttpClientOptions()));
            case FACEBOOK:
                return OAuth2Auth.newInstance(io.vertx.ext.auth.oauth2.providers.FacebookAuth.create(vertx.getDelegate(), clientId, clientSecret, HttpHelper.getHttpClientOptions()));
            case TWITTER:
                return OAuth2Auth.newInstance(io.vertx.ext.auth.oauth2.providers.TwitterAuth.create(vertx.getDelegate(), clientId, clientSecret, HttpHelper.getHttpClientOptions()));
            default:
                throw new NotImplementedException(type + ": provider type is not implemented");
        }
    }

    public JsonObject getOAuth2AuthorizationURL(final JsonObject params);

    public Future<JsonObject> getOAuth2AccessToken(final JsonObject params);

    public Future<JsonObject> doPost(final JsonObject payload);

    public Future<JsonObject> doGetProfile(final JsonObject accessToken);
}
