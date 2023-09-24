package com.cydeo.service;

import com.cydeo.dto.AccountDTO;

import java.util.List;

/**
 * The business logic is to create an account and store the list of created accounts.
 * The common practice is to make services with abstract methods.
 * This interface contains abstract methods, the first abstract method is to create
 * an account with its required parameters. The required parameter are, the account type,
 * the balance of the account, the date of creation, and a user id.
 * The second abstract method stores a list of the created accounts.
 * Once an account is created, the user should be able to delete and activate an
 * account. To do so, two more abstract methods are required
 * the deleteAccount and activateAccount.
 */
public interface AccountService {

    //business logic creating a new account
    void createNewAccount(AccountDTO accountDTO);

    //business logic storing list of accounts,
    List<AccountDTO> listAllAccounts();

    //business logic to delete an account
    void deleteAccount(Long id);

    //business logic to activate an account
    void activateAccount(Long id);

    AccountDTO retrieveById(Long id);

    List<AccountDTO> listAllActiveAccounts();

    void updateAccount(AccountDTO receiverAccount);
}
