package com.cydeo.repository;

import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
        List<Account> findAllByAccountStatus(AccountStatus active);
}
