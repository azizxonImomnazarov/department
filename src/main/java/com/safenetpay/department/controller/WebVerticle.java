package com.safenetpay.department.controller;

import com.safenetpay.department.dao.DataBaseVerticle;
import com.safenetpay.department.dao.Repository;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public class WebVerticle extends AbstractVerticle {

  private final Repository repository = new DataBaseVerticle();

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
  }

  private void updateDepartment(RoutingContext rc) {
  }

  private void saveDepartment(RoutingContext rc) {
  }

  private void getDepartment(RoutingContext rc) {
  }

  private void updateEmployee(RoutingContext rc) {

  }

  private void getEmployees(RoutingContext rc) {
    rc.response().putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
    .end(repository.)
  }

  private void saveEmployee(RoutingContext rc) {
    
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
