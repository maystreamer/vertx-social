package com.greyseal.vertx.social.handler;

import com.greyseal.vertx.boot.Constant.MediaType;
import com.greyseal.vertx.boot.annotation.RequestMapping;
import com.greyseal.vertx.boot.handler.BaseHandler;
import com.greyseal.vertx.social.apiprovider.IApiProvider;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.RoutingContext;

@RequestMapping(path = "/social")
public class SocialHandler extends BaseHandler {

    public SocialHandler(final Vertx vertx) {
        super(vertx);
    }

    @Override
    @RequestMapping(path = "/authorizationurl", method = HttpMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void handle(RoutingContext event) {
        try {
            final JsonObject params = event.getBodyAsJson();
            final String providerName = event.request().getParam("provider");
            final JsonObject response = IApiProvider.loadApiProvider(providerName, event.vertx()).getOAuth2AuthorizationURL(params);
            event.setBody(Buffer.buffer(response.toString()));
            event.next();
        } catch (Exception ex) {
            event.fail(ex);
        }
    }

    @RequestMapping(path = "/accesstoken", method = HttpMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void getOAuth2AccessToken(RoutingContext event) {
        try {
            final JsonObject params = event.getBodyAsJson();
            final String providerName = event.request().getParam("provider");
            IApiProvider.loadApiProvider(providerName, event.vertx()).getOAuth2AccessToken(params).setHandler(handler -> {
                if (!handler.failed()) {
                    final JsonObject json = handler.result();
                    event.setBody(Buffer.buffer(json.toString()));
                    event.next();
                } else {
                    event.fail(handler.cause());
                }
            });
        } catch (Exception ex) {
            event.fail(ex);
        }
    }

    @RequestMapping(path = "/profile", method = HttpMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void getProfile(RoutingContext event) {
        try {
            final JsonObject accessToken = event.getBodyAsJson();
            final String providerName = event.request().getParam("provider");
            IApiProvider.loadApiProvider(providerName, event.vertx()).doGetProfile(accessToken).setHandler(handler -> {
                if (!handler.failed()) {
                    event.setBody(Buffer.buffer(handler.result().toString()));
                    event.next();
                } else {
                    event.fail(handler.cause());
                }
            });
        } catch (Exception ex) {
            event.fail(ex);
        }
    }
}
