package com.greyseal.vertx.social.apiprovider;

import com.greyseal.vertx.social.helper.HttpHelper;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.auth.oauth2.AccessToken;
import io.vertx.reactivex.ext.auth.oauth2.OAuth2Auth;
import org.apache.commons.lang3.NotImplementedException;

public abstract class AbstractApiProvider implements IApiProvider {
    protected OAuth2Auth oAuth2AuthProvider;

    public AbstractApiProvider(final Vertx vertx, final String clientId, final String clientSecret, final ApiProviderName type) {
        switch (type) {
            case LINKEDIN:
                oAuth2AuthProvider = OAuth2Auth.newInstance(io.vertx.ext.auth.oauth2.providers.LinkedInAuth.create(vertx.getDelegate(), clientId, clientSecret, HttpHelper.getHttpClientOptions()));
                break;
            case GITHUB:
                oAuth2AuthProvider = OAuth2Auth.newInstance(io.vertx.ext.auth.oauth2.providers.GithubAuth.create(vertx.getDelegate(), clientId, clientSecret, HttpHelper.getHttpClientOptions()));
                break;
            case FACEBOOK:
                oAuth2AuthProvider = OAuth2Auth.newInstance(io.vertx.ext.auth.oauth2.providers.FacebookAuth.create(vertx.getDelegate(), clientId, clientSecret, HttpHelper.getHttpClientOptions()));
                break;
            case TWITTER:
                oAuth2AuthProvider = OAuth2Auth.newInstance(io.vertx.ext.auth.oauth2.providers.TwitterAuth.create(vertx.getDelegate(), clientId, clientSecret, HttpHelper.getHttpClientOptions()));
                break;
            default:
                throw new NotImplementedException(type + ": provider type is not implemented");
        }
    }

    /**
     * @param {"scopes":["r_basicprofile","r_emailaddress","rw_company_admin","w_share"],"redirect_uri":"https://www.huwats.com/","state":"STATE"} sample for Linkedin
     * @return string: authorization url
     */
    @Override
    public JsonObject getOAuth2AuthorizationURL(final JsonObject params) {
        JsonObject _params = params.copy();
        return new JsonObject().put("authorizationURL", oAuth2AuthProvider.authorizeURL(params));
    }

    public Future<JsonObject> getOAuth2AccessToken(final JsonObject params) {
        Future<JsonObject> future = Future.future();
        JsonObject _params = params.copy();
        oAuth2AuthProvider.rxGetToken(params).subscribe(response -> {
            System.out.println(response.principal().toString());
            future.complete(response.principal());
        }, error -> {
            future.fail(error);
        });
        return future;
    }

    public Future<AccessToken> decodeOAuth2AccessToken(final String accessToken) {
        Future<AccessToken> future = Future.future();
        oAuth2AuthProvider.rxDecodeToken(accessToken).subscribe(response -> {
            future.complete(response);
        }, error -> {
            future.fail(error);
        });
        return future;
    }

    public Future<JsonObject> doFetch(final AccessToken accessToken, final HttpMethod method, final String resource, final JsonObject headers, final Buffer payload) {
        Future<JsonObject> future = Future.future();
        accessToken.rxFetch(method, resource, headers, payload != null ? payload : Buffer.buffer()).subscribe(response -> {
            future.complete(response.body().toJsonObject());
        }, error -> {
            future.fail(error);
        });
        return future;
    }

    public Future<JsonObject> doGetProfile(final String accessToken, final String resourceURL, final JsonObject headers) {
        Future<JsonObject> future = Future.future();
        decodeOAuth2AccessToken(accessToken).setHandler(response -> {
            if (!response.failed()) {
                final AccessToken _accessToken = response.result();
                doFetch(_accessToken, HttpMethod.GET, resourceURL, headers, null).setHandler(handler -> {
                    if (!handler.failed()) {
                        future.complete(handler.result());
                    } else {
                        future.fail(handler.cause());
                    }
                });
            } else {
                future.fail(response.cause());
            }
        });
        return future;
    }

}