package com.cydeo.service.impl;

import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.dto.AccountDTO;
import com.cydeo.mapper.AccountMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {


    //private final forces to create a constructor, must add @component annotation on both classes
    //this class and the AccountRepository
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

   //injecting by a constructor, takes the AccountRepository as a parameter
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    /**
     * This method creates account, stores the accounts into the database,
     * and return the created object
     * @return added account
     */
    @Override
    public void createNewAccount(AccountDTO accountDTO) {
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        accountDTO.setCreationDate(new Date());
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }

    /**
     * Since this method is returning list of AccountDTO
     * The list of accounts that are fetched from the db
     * must be converted to DTOs then returned
     * @return list of accounts fetched from the db and converted to STOs
     */
    @Override
    public List<AccountDTO> listAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        //find the account object based on id
        Optional<Account> account = accountRepository.findById(id);
        //update the accountStatus of that object.
        accountMapper.convertToDTO(account.get()).setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateAccount(Long id) {
        //find the account object based on id
        Optional<Account> account = accountRepository.findById(id);
        //update the accountStatus of that object.
        accountMapper.convertToDTO(account.get()).setAccountStatus(AccountStatus.ACTIVE);

    }

    @Override
    public AccountDTO retrieveAccountById(Long id) {
        return null;
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        return accountMapper.convertToDTO(accountRepository.findById(id).get());
    }
}
