package com.cydeo.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * The common practice is to define the business logic attributes
 * in the model package. The transaction will need to have a unique user identifier
 * to handle the amount, handle the receiver and sender, mark the date,
 * and send an informative message
 */
@Data
@Builder
public class Transaction {

    private UUID sender;//to uniquely identify a sender
    private UUID receiver;//to uniquely identify a receiver
    private BigDecimal amount;//to store the amount sent or received
    private String message; //to store a message
    private Date creationDate; //to store date of transfer
}
