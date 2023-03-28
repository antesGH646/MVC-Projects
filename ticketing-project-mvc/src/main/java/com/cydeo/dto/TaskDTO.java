package com.cydeo.dto;

import com.cydeo.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Do not need @AllArgsConstructor because, do not want the ID to be assigned in the
 * constructor. The ID should come from the database, otherwise when creating new IDs
 * it will throw error due to null values (not in the DataGenerator)
 */
@NoArgsConstructor
@Data
public class TaskDTO {

    private Long id;
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject;
    private String taskDetail;
    private Status taskStatus;
    private LocalDate assignedDate;

    //constructor without the primary key(id), b/c it comes from the Postgres(database)
    public TaskDTO(ProjectDTO project, UserDTO assignedEmployee, String taskSubject, String taskDetail, Status taskStatus, LocalDate assignedDate) {
        this.project = project;
        this.assignedEmployee = assignedEmployee;
        this.taskSubject = taskSubject;
        this.taskDetail = taskDetail;
        this.taskStatus = taskStatus;
        this.assignedDate = assignedDate;
        //assigning the id, but it is not included in the method signature or parameter
        this.id = UUID.randomUUID().getMostSignificantBits();
    }
}
