package com.cydeo.repository;

import com.cydeo.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepository {

    //stores list of transactions
    public static List<Transaction> transactionList = new ArrayList<>();

    /**
     * Adds a transaction into the list and returns it
     * @param transaction AccountType
     * @return the list of AccountType
     */
    public Transaction addTransaction(Transaction transaction){
        transactionList.add(transaction);//adds the transaction into a list
        return transaction; //returns an individual transaction
    }

    /**
     * finds individual transactions
     * @return a list of transactions
     */
    public List<Transaction> findAllTransactions() {
        return transactionList;//returns the above list of transactions
    }

    /**
     * Finds the last 10 transactions in a sorting order
     * based on the creation date
     * @return a list of last 10 transactions
     */
    public List<Transaction> findLast10Transactions() {
        return transactionList.stream()
                .sorted(Comparator.comparing(Transaction::getCreationDate).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * If an account id is used by either a sender or a receiver,
     * this method finds list of transactions by id, then returns the transactions of the id
     * @param id UUID
     * @return list of transactions
     */
    public List<Transaction> findTransactionListById(UUID id) {
        return transactionList.stream()
                .filter(transaction -> transaction.getSender().equals(id)
                        || transaction.getReceiver().equals(id))
                .collect(Collectors.toList());
    }
}
