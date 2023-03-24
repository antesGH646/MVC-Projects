package com.cydeo.service;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The business logic is to make a transaction and store list of transactions.
 * The common practice is to make services with abstract methods.
 * This class contains two abstract methods, the first abstract method is
 * make a transaction with required parameters. To a transaction the account type,
 * the balance of the account, the date of creation, and a user id are required.
 * The second abstract method stores a list of the transactions.
 */
public interface TransactionService {

    //makes transactions
    Transaction makeTransfer(Account sender, Account receiver,
                             BigDecimal amount, Date creationDate, String message);

    //finds all transactions and stores them in a list
    List<Transaction> findAllTransaction();
}
