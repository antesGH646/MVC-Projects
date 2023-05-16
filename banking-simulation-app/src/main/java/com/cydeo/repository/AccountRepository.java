package com.cydeo.repository;

import com.cydeo.exception.RecordNotFoundException;
import com.cydeo.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {
    //stores list of account type
    public static List<Account> accountList = new ArrayList<>();

    /**
     * Adds an account into the list and returns it
     * @param account AccountType
     * @return the list of AccountType
     */
    public Account addAccount(Account account) {
        accountList.add(account);//adds an account into the list
        return account;//returns each added individual account
    }

    /**
     * Holds individual accounts
     * @return a list of accounts
     */
    public List<Account> findAllAccounts() {
        return accountList; //returns the above list of accounts
    }

    /**
     * This method finds an account by an id.
     * The target id must match or must exist within the list of the accounts
     * otherwise it throws an exception
     * @param id account id
     * @return matching id
     */
    public Account findAccountById(UUID id) {
        //finds an account from the list of accounts by id, otherwise throws an exception (if it doesn't exist)
        return accountList.stream().filter(account -> account.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new RecordNotFoundException("Account not exist in the database."));
    }
}
