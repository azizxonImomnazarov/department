package com.safenetpay.department.controller;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public class WebVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        configureRouter()
        .compose(this::startHTTPServer)
        .onComplete(startPromise::handle);
    }
 
  private Future<Router> configureRouter() {
    Promise<Router> promise = Promise.<Router>promise();
    Router router = Router.router(vertx);
    router.route("/*").handler(StaticHandler.create());
    router.get("/api/employee/list").handler(this::getEmployees);
    router.post("/api/employee").handler(this::saveEmployee);
    router.put("/api/employee").handler(this::updateEmployee);
    router.get("/api/department/list").handler(this::getEmployees);
    router.post("/api/department").handler(this::saveEmployee);
    router.put("/api/department").handler(this::updateEmployee);
    router.get("/api/department/list").handler(this::getEmployees);
    router.post("/api/department").handler(this::saveEmployee);
    router.put("/api/department").handler(this::updateEmployee);
    promise.complete(router);
    return promise.future();
  }

  private Object updateEmployee(RoutingContext rc) {

    return null;
  }

  private Object getEmployees(RoutingContext rc) {

    return null;
  }

  private Object saveEmployee(RoutingContext rc) {

    return null;
  }

  private Future<Void> startHTTPServer(Router router) {
    JsonObject http = config().getJsonObject("http");

    int port = http.getInteger("port");
    String hostName = http.getString("host");

    HttpServer server = vertx
    .createHttpServer()
    .requestHandler(router);
  
    return Future.<HttpServer>future(promise -> server.listen(port,hostName,promise)).mapEmpty();
  }
}
