package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<TransactionDTO> findAllTransactions();

    List<TransactionDTO> findLast10Transactions();

    List<TransactionDTO> findTransactionListById(UUID id);
}
