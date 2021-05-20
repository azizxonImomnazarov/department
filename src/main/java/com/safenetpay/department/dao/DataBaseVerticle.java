package com.safenetpay.department.dao;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

public class DataBaseVerticle extends AbstractVerticle implements Repository {

    private PgConnectOptions connectOptions;
    private PoolOptions poolOptions;
    private PgPool client;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        doDataBaseMigration()
        .onComplete(startPromise::handle);
    }

    private Future<Void> doDataBaseMigration() {
        JsonObject dbConfig = config().getJsonObject("db");
        int port = dbConfig.getInteger("port");
        String host = dbConfig.getString("host");
        String dbName = dbConfig.getString("dbName");
        String user = dbConfig.getString("user");
        String password = dbConfig.getString("password");

        connectOptions = new PgConnectOptions()
            .setPort(port)
            .setHost(host)
            .setDatabase(dbName)
            .setUser(user).setPassword(password);

        poolOptions = new PoolOptions().setMaxSize(5);

        client = PgPool.pool(vertx, connectOptions, poolOptions);

        return Future.<Void>succeededFuture();
    }
    
    private void updateTask() {

    }

    private void saveTask() {

    }

    private void getTask() {

    }

    private void updateDepartment() {

    }

    private void saveDepartment() {

    }

    private void getDepartment() {

    }

    private void updateEmployee() {

    }
}
