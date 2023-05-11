package com.cydeo.dto;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * The common practice is to define the business logic attributes
 * in the model package. The account will need to have a unique user identifier
 * to handle the amount, handle the receiver and sender, mark the date,
 * and send an informative message
 */
@Data
@Builder
public class AccountDTO {
    private UUID id; //Universal Unique Identifier
    @NotNull
    @Positive
    private BigDecimal balance; //handles big decimals and is more precise
    @NotNull
    private AccountType accountType;//to handle checking and saving accounts
    private Date creationDate;//the handle the creating date
    @NotNull
    private Long userId; //to uniquely identify a user
    private AccountStatus accountStatus;
}
