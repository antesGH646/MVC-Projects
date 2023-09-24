package com.cydeo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The common practice is to define the business logic attributes
 * in the model package. The transaction will need to have a unique user identifier
 * to handle the amount, handle the receiver and sender, mark the date,
 * and send an informative message
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    //declare fields/attributes

    @NotNull
    private AccountDTO receiver;//to uniquely identify a receiver

    @NotNull
    private AccountDTO sender;//to uniquely identify a sender

    @NotNull
    @Positive
    private BigDecimal amount;//to store the amount sent or received

    @NotNull
    @Size(min = 2,max = 250)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String message; //to store a message

    private Date creationDate; //to store date of transfer
}
