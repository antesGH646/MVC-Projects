package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")//this clause will automatically be added/concatenated,
// whatever repository method that uses the Project entity has additional filtering,
// in the DB table, a row/data is_Deleted set to false will be fetched & used by whatever repository method
public class Project extends BaseEntity{

    @Column(unique = true)//to avoid project code duplication
    private String projectCode;
    @Column(unique = true)//to prevent project name duplication
    private String projectName;
    
    @ManyToOne(fetch = FetchType.LAZY)//one manager can be assigned to many projects
    @JoinColumn(name = "manager_id")
    private User assignedManager;

    @Column(columnDefinition = "Date")
    private LocalDate startDate;

    @Column(columnDefinition = "Date")
    private LocalDate endDate;

    private String projectDetail;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;
}
