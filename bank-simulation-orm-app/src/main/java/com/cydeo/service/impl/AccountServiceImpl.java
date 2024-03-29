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
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {


    //private final forces to create a constructor, must add @component annotation
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
        //get all the active accounts from the repository
        List<Account> accountList = accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE);
        //convert all the list of active accounts to accountDto and then return them
        return accountList.stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        //find the account object based on id
        Account account = accountRepository.findById(id).get();
        //set the fetched object account status to DELETED
        account.setAccountStatus(AccountStatus.DELETED);
        //save the updated account object.
        accountRepository.save(account);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        //find the account entity based on id, then convert it to dto and then return it
        return accountMapper.convertToDTO(accountRepository.findById(id).get());
    }

    @Override
    public void activateAccount(Long id) {
        //find the account object based on id
        Account account = accountRepository.findById(id).get();
        //set the fetched object account status to ACTIVE.
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);

    }

    @Override
    public List<AccountDTO> listAllActiveAccounts() {
        //we need active accounts from repository
        List<Account> accountList = accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE);
        //convert active accounts to accountDto and return
        return accountList.stream().map(accountMapper::convertToDTO).collect(Collectors.toList());

    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }

}
