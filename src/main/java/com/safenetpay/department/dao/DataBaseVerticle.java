package com.safenetpay.department.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.safenetpay.department.model.Employee;
import com.safenetpay.department.model.Task;

import org.apache.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

public class DataBaseVerticle extends AbstractVerticle implements Repository {

    private PgConnectOptions connectOptions;
    private PoolOptions poolOptions;
    private PgPool client;

    private static final Logger LOGGER = Logger.getLogger(DataBaseVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        doDataBaseMigration().compose(this::configureConsumers).onComplete(startPromise::handle);
    }

    private Future<Void> doDataBaseMigration() {
        JsonObject dbConfig = config().getJsonObject("db");
        int port = dbConfig.getInteger("port");
        String host = dbConfig.getString("host");
        String dbName = dbConfig.getString("dbName");
        String user = dbConfig.getString("user");
        String password = dbConfig.getString("password");

        connectOptions = new PgConnectOptions().setPort(port).setHost(host).setDatabase(dbName).setUser(user)
                .setPassword(password);

        poolOptions = new PoolOptions().setMaxSize(5);

        client = PgPool.pool(vertx, connectOptions, poolOptions);

        return Future.<Void>succeededFuture();
    }

    public Future<Void> configureConsumers(Void unused) {
        vertx.eventBus().consumer("get_employees").handler(this::getEmployees);
        vertx.eventBus().consumer("get_departments").handler(this::getDepartment);
        vertx.eventBus().consumer("get_tasks").handler(this::getTask);
        vertx.eventBus().consumer("save_departments").handler(this::saveDepartment);
        return Future.<Void>succeededFuture();
    }

    @Override
    public void updateTask() {

    }

    @Override
    public void saveTask() {

    }

    @Override
    public void getTask(Message<Object> mes) {
        client.preparedQuery("SELECT * FROM get_task()").execute().onComplete(ar -> {
            if (ar.succeeded()) {
                RowSet<Row> rowset = ar.result();
                List<Task> tasks = new LinkedList<>();
                for (Row row : rowset) {
                    Task task = new Task();
                    task.setTaskId(row.getLong("task_id"));
                    task.setTaskDescription(row.getString("task_description"));
                    task.setEnd(row.getBoolean("task_is_end"));
                    task.setPresentDate(row.getLocalDate("task_present_date"));
                    task.setCompletedDate(row.getLocalDate("task_completed_date"));
                    tasks.add(task);
                }
                mes.reply(Json.encode(tasks));
            } else {
                LOGGER.error(ar.cause());
            }
        });
    }

    @Override
    public void updateDepartment() {

    }

    @Override
    public void saveDepartment(Message<Object> mes) {
        
        JsonObject dep = (JsonObject) mes.body();
        client.preparedQuery("insert into snp_department(department_name) values($1)")
                .execute(Tuple.of(dep.getString("departmentName"))).onComplete(ar -> {
                    if (ar.succeeded()) {
                        mes.reply(new JsonObject().put("success", true));
                        LOGGER.info("object is saved to database");
                    } else {
                        mes.reply(new JsonObject().put("success", false));
                        LOGGER.error(ar.cause());
                    }
                });
    }

    @Override
    public void updateEmployee() {

    }

    @Override
    public void getEmployees(Message<Object> mes) {
        client.preparedQuery("SELECT * FROM get_employee()").execute().onComplete(ar -> {
            if (ar.succeeded()) {
                RowSet<Row> rowset = ar.result();
                Map<Long, Employee> map = new HashMap<>();
                for (Row row : rowset) {
                    Employee employee;
                    Long employeeId = row.getLong("employee_id");
                    if (map.get(employeeId) != null) {
                        employee = map.get(employeeId);
                    } else {
                        employee = new Employee();
                        employee.setEmployeeId(employeeId);
                        employee.setEmployeeName(row.getString("employee_name"));
                        employee.setEmployeeSurName(row.getString("employee_sur_name"));
                    }
                    Task task = new Task();
                    task.setTaskId(row.getLong("task_id"));
                    task.setTaskDescription(row.getString("task_description"));
                    task.setEnd(row.getBoolean("task_is_end"));
                    task.setPresentDate(row.getLocalDate("task_present_date"));
                    task.setCompletedDate(row.getLocalDate("task_completed_date"));
                    employee.addTask(task);

                    map.put(employee.getEmployeeId(), employee);
                }
                mes.reply(Json.encode(map.values()));
            } else {
                LOGGER.error(ar.cause());
            }
        });
    }

    @Override
    public void saveEmployee() {

    }

    @Override
    public void getDepartment(Message<Object> mes) {
        // TODO Auto-generated method stub
        
    }
}
