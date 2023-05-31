package com.cydeo.repository;

import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select * from transactions order by creation_date desc limit 10", nativeQuery = true)
    List<Transaction> findLast10Transactions();

    @Query("select t from Transaction t where t.sender.id = ?1 or t.receiver.id = ?1")
    List<Transaction> findTransactionListId(@Param("id") Long id);
}
