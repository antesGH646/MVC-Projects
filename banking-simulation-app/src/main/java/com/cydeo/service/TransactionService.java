package com.cydeo.service;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The business logic is to make a transaction and store list of transactions.
 * The common practice is to make services with abstract methods.
 * This service class contains abstract methods, the first abstract method is
 * make a transaction with required parameters. To make a transaction you need
 * the account type, the balance of the account, the date of creation, and a user id.
 * The second abstract method stores a list of all the transactions.
 * The third abstract method stores a list of 10 transactions
 * The fourth abstract method stores a list of found by their IDs
 */
public interface TransactionService {

    //makes transactions
    Transaction makeTransfer(Account sender, Account receiver,
                             BigDecimal amount, Date creationDate, String message);

    //finds all transactions and stores them in a list
    List<Transaction> findAllTransaction();

    List<Transaction> last10Transactions();


    List<Transaction> findTransactionListByID(UUID id);
}
