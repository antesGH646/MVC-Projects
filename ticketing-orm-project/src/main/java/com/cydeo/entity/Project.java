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
@Where(clause = "is_deleted=false")
public class Project extends BaseEntity{

    private String projectCode;
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
