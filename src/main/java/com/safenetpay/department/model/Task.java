package com.safenetpay.department.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Task {

    private Long taskId;

    private String taskDescription;

    private boolean isEnd;

    private LocalDate presentDate;

    private LocalDate completedDate; 
    
}
