package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepository{

    //stores list of transactions
    public static List<TransactionDTO> transactionDTOList = new ArrayList<>();

    /**
     * Adds a transaction into the list and returns it
     * @param transactionDTO AccountType
     * @return the list of AccountType
     */
    public TransactionDTO addTransaction(TransactionDTO transactionDTO){
        transactionDTOList.add(transactionDTO);//adds the transaction into a list
        return transactionDTO; //returns an individual transaction
    }

    /**
     * finds individual transactions
     * @return a list of transactions
     */
    public List<TransactionDTO> findAllTransactions() {
        return transactionDTOList;//returns the above list of transactions
    }

    /**
     * Finds the last 10 transactions in a sorting order
     * based on the creation date
     * @return a list of last 10 transactions
     */
    public List<TransactionDTO> findLast10Transactions() {
        return transactionDTOList.stream()
                .sorted(Comparator.comparing(TransactionDTO::getCreationDate).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * If an account id is used by either a sender or a receiver,
     * this method finds list of transactions by id, then returns the transactions of the id
     * @param id UUID
     * @return list of transactions
     */
    public List<TransactionDTO> findTransactionListById(UUID id) {
        return transactionDTOList.stream()
                .filter(transactionDTO -> transactionDTO.getSender().equals(id)
                        || transactionDTO.getReceiver().equals(id))
                .collect(Collectors.toList());
    }
}
