package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<TransactionDTO> findAllTransactions();

    List<TransactionDTO> findLast10Transactions();

    List<TransactionDTO> findTransactionListById(UUID id);
}
