package com.safenetpay.department.model;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Department {
    
    private Long departmentId;

    private String departmentName;

    private List<Employee> employeeList;

    public void addEmployee(Employee employee) {
        if (employeeList == null) {
            employeeList = new LinkedList<>();
        }
        employeeList.add(employee);
    }
}
