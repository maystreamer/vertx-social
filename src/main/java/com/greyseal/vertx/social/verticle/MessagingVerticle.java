package com.greyseal.vertx.social.verticle;

import com.greyseal.vertx.boot.Constant.VerticleType;
import com.greyseal.vertx.boot.annotation.Verticle;
import com.greyseal.vertx.boot.verticle.BaseVerticle;
import io.vertx.core.Future;

@Verticle(type = VerticleType.STANDARD, configuration = "messaging_verticle")
public class MessagingVerticle extends BaseVerticle {

    @Override
    public void start(Future<Void> startFuture) {
        try {
            super.start();
            startFuture.complete();
        } catch (Exception ex) {
            logger.error("Failed to start HTTP Server ", ex);
            startFuture.fail(ex);
        }
    }

    @Override
    public void stop() throws Exception {

    }
}
