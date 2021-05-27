package com.safenetpay.department.controller;

import com.safenetpay.department.dao.DataBaseVerticle;

import org.apache.log4j.Logger;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

public class WebVerticle extends AbstractVerticle {

  private static final Logger LOGGER = Logger.getLogger(WebVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    configureRouterBuilder()
    .compose(this::configureRouter)
    .compose(this::startHTTPServer)
    .onComplete(startPromise::handle);
  }

  private Future<RouterBuilder> configureRouterBuilder() {
    Promise<RouterBuilder> promise = Promise.promise();
    RouterBuilder.create(vertx, "src/main/resources/webroot/swagger/swagger.yaml", ar -> {
      if (ar.succeeded()) {
        LOGGER.info("RouterBuilder is confugured");
        promise.complete(ar.result());
      } else {
        LOGGER.error(ar.cause());
      }
    });
    return promise.future();
  }

  private Future<Router> configureRouter(RouterBuilder routerBuilder) {
    Promise<Router> promise = Promise.<Router>promise();
    Router router = routerBuilder.createRouter();
    router.route().handler(BodyHandler.create());
    router.route("/*").handler(StaticHandler.create());
    router.get("/api/employee/list").handler(this::getEmployees);
    router.post("/api/employee").handler(this::saveEmployee);
    router.put("/api/employee").handler(this::updateEmployee);
    router.get("/api/department/list").handler(this::getDepartment);
    router.post("/api/department").handler(this::saveDepartment);
    router.put("/api/department").handler(this::updateDepartment);
    router.get("/api/task/list").handler(this::getTask);
    router.post("/api/task").handler(this::saveTask);
    router.put("/api/task").handler(this::updateTask);
    promise.complete(router);
    return promise.future();
  }

  private void updateTask(RoutingContext rc) {

  }

  private void saveTask(RoutingContext rc) {
  }

  private void getTask(RoutingContext rc) {
    vertx.eventBus().request("get_tasks", "", reply -> {
      rc.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
          .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*").end(reply.result().body().toString());
    });
  }

  private void updateDepartment(RoutingContext rc) {
  }

  private void saveDepartment(RoutingContext rc) {
    vertx.eventBus().request("save_departments", rc.getBodyAsJson(), reply -> {
      LOGGER.info("Request is handle to save department");
      rc.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
          .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*").end(reply.result().body().toString());
    });
  }

  private void getDepartment(RoutingContext rc) {
    vertx.eventBus().request("get_departments", "", reply -> {
      rc.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
          .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*").end(reply.result().body().toString());
    });
  }

  private void updateEmployee(RoutingContext rc) {

  }

  private void getEmployees(RoutingContext rc) {
    vertx.eventBus().request("get_employees", "", reply -> {
      rc.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
          .putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*").end(reply.result().body().toString());
    });
  }

  private void saveEmployee(RoutingContext rc) {

  }

  private Future<Void> startHTTPServer(Router router) {
    JsonObject http = config().getJsonObject("http");

    int port = http.getInteger("port");
    String hostName = http.getString("host");

    HttpServer server = vertx.createHttpServer().requestHandler(router);

    return Future.<HttpServer>future(promise -> server.listen(port, hostName, promise)).mapEmpty();
  }
}
