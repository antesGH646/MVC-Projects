package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")//this clause will automatically be added/concatenated,
// whatever repository method that uses the Task entity has additional filtering,
// in the DB table, a row/data is_Deleted set to false will be fetched & used by whatever repository method
public class Task extends BaseEntity{
    private String taskSubject;
    private String taskDetail;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Column(columnDefinition = "Date")
    private LocalDate assignedDate;

    @ManyToOne(fetch = FetchType.LAZY)//many tasks can be assigned to one employee
    private User assignedEmployee;

    @ManyToOne(fetch = FetchType.LAZY)//many tasks can be assigned to one project
    private Project project;

}
