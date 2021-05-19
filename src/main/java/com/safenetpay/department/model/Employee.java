package com.safenetpay.department.model;

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

    private Long departmentId;
}
