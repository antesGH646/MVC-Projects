package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime insertDateTime;
    private Long insertUserId;
    private LocalDateTime lastUpdateDateTime;
    private Long lastUpdateUserId;

    //in real life deleting a row from a database is not good practice,
    // however most companies implement triggering, and use flags
    private Boolean isDeleted=false;

}
