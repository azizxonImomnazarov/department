package com.safenetpay.department.dao;

import io.vertx.core.eventbus.Message;

public interface Repository {

    void updateTask();

    void saveTask();

    void getTask(Message<Object> mes);

    void updateDepartment();

    void saveDepartment(Message<Object> mes);

    void getDepartment(Message<Object> mes);

    void updateEmployee();

    void getEmployees(Message<Object> mes);

    void saveEmployee();

}
