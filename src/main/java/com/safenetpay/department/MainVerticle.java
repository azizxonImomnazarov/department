package com.safenetpay.department;

import com.safenetpay.department.controller.WebVerticle;
import com.safenetpay.department.dao.DataBaseVerticle;

import org.apache.log4j.Logger;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

  private final static Logger LOGGER = Logger.getLogger(MainVerticle.class);

  private final JsonObject loadedConfig = new JsonObject();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOGGER.debug("Application starting ....");

     doConfig()
      .compose(this::storeConfig)
      .compose(this::deploySomeOtherVerticles)
      .onComplete(startPromise::handle);

  }

  private Future<JsonObject> doConfig() {
    ConfigStoreOptions storeOptions = new ConfigStoreOptions()
    .setType("file")
    .setFormat("json")
    .setConfig(new JsonObject().put("path", "config.json"));

    ConfigRetrieverOptions retrieverOptions = new ConfigRetrieverOptions()
    .addStore(storeOptions);

    ConfigRetriever configRetriever = ConfigRetriever.create(vertx,retrieverOptions);

    return Future.future(promise -> configRetriever.getConfig(promise));
  }

  private Future<Void> storeConfig(JsonObject config) {
    loadedConfig.mergeIn(config);
    return Future.<Void>succeededFuture();
  }

  private Future<Void> deploySomeOtherVerticles(Void unused) {
    DeploymentOptions options = new DeploymentOptions()
      .setConfig(loadedConfig);
    
    Future<String> future1 = Future.future(promise -> vertx.deployVerticle(new DataBaseVerticle(),options,promise));
    Future<String> future2 = Future.future(promise -> vertx.deployVerticle(new WebVerticle(),options,promise));

    return CompositeFuture.all(future1, future2).mapEmpty();
  }
}
