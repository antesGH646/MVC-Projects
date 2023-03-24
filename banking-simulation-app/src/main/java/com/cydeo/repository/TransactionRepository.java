package com.cydeo.repository;

import com.cydeo.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionRepository{

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
     * Holds individual transactions
     * @return a list of transactions
     */
    public List<Transaction> allTransactions() {
        return transactionList;
    }
}
