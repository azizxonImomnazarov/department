package com.safenetpay.department.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Employee {

    private Long employeeId;

    private String employeeName;

    private String employeeSurName;

    private List<Task> taskList;

    public void addTask(Task task) {
        if (taskList == null) {
            taskList = new ArrayList<>();
        }
        taskList.add(task);
    }
}
