package com.cydeo.service;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The business logic is to create an account and store the list of account created.
 * The common practice is to make services with abstract methods.
 * This class contains two abstract methods, the first abstract method is to create
 * an account with its required parameters. To create an account the account type,
 * the balance of the account, the date of creation, and a user id are required.
 * The second abstract method stores a list of the created accounts.
 */
public interface AccountService {

    //business logic creating a new account
    Account createNewAccount(BigDecimal balance, Date creationDate,
                             AccountType accountType,Long userId);

    //business logic storing list of accounts,
    List<Account> listAllAccount();

    void deleteAccount(UUID id);

    void activateAccount(UUID id);
}
