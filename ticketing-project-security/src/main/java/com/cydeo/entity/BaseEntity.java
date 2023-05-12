package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)//to make the column not updatable and not null
    private LocalDateTime insertDateTime;
    @Column(nullable = false, updatable = false)
    private Long insertUserId;//security dynamically tracks the changes made by the app users
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;//security puts the date and time in the table
    @Column(nullable = false)
    private Long lastUpdateUserId;//security tracks the changes, puts the id in the db

    //in real life deleting a row from a database is not good practice,
    // however most companies implement triggering, and use flags
    private Boolean isDeleted=false;

    /**
     * For every action in the database this method will be executed to
     * record the user id with date and time
     * The fields are initialized in this method
     * Spring understands this method by the @PrePersis annotation
     */
    @PrePersist//marks/tells spring to execute this code for every action done in the db
    public void onPrePersist() {
        this.insertDateTime = LocalDateTime.now();
        this.insertUserId= 1L;
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = 1L;
    }

    /**
     * For update in the database this method will be executed to
     * record the user id with date and time
     * The fields are initialized in this method
     * Spring understands this method by the @PreUpdate annotation
     */
    @PreUpdate
    public void onPreUpdate() {
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = 1L;
    }

}
