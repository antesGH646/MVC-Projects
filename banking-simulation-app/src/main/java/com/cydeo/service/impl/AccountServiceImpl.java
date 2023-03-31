package com.cydeo.service.impl;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {


    //private final forces to create a constructor, must add @component annotation on both classes
    //this class and the AccountRepository
   private final AccountRepository accountRepository;

   //injecting by a constructor, takes the AccountRepository as a parameter
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * This method creates account, stores the accounts into the database,
     * and return the created object
     * @param balance BigDecimal
     * @param creationDate Date
     * @param accountType AccountType
     * @param userId id
     * @return added account
     */
    @Override
    public Account createNewAccount(BigDecimal balance, Date creationDate,
                                    AccountType accountType, Long userId) {
       //create an account object
        Account account = Account.builder().id(UUID.randomUUID())
                .userId(userId).balance(balance).accountType(accountType).creationDate(creationDate)
                .accountStatus(AccountStatus.ACTIVE).build();
        //saving the account object into the database

        //returning the created account object

        return accountRepository.addAccount(account);
    }

    /**
     * This method returns a list of all accounts
     * @return list of all accounts
     */
    @Override
    public List<Account> listAllAccounts() {
        return accountRepository.findAllAccounts();
    }

    /**
     * This method deletes an account
     * @param id UUID
     */
    @Override
    public void deleteAccount(UUID id) {
        //find the account object based on id
        Account account = accountRepository.findAccountById(id);
        //update the accountStatus of that object.
        account.setAccountStatus(AccountStatus.DELETED);
    }

    /**
     * This method activates a deleted account
     * @param id UUID
     */
    @Override
    public void activateAccount(UUID id) {
        //find the account object based on id
        Account account = accountRepository.findAccountById(id);
        //update the accountStatus of that object.
        account.setAccountStatus(AccountStatus.ACTIVE);
    }

    /**
     * This method get an account by its id
     * @param id UUID
     * @return returns an Account by its id
     */
    @Override
    public Account retrieveAccountById(UUID id) {
        return accountRepository.findAccountById(id);
    }
}
