package com.cydeo.service.impl;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.dto.AccountDTO;
import com.cydeo.mapper.TransactionMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    //@Value() read properties file accepts the value with "${key}"
    //For everything to work properly, the key is set to false in the properties files
    @Value("${under_construction}")
    private boolean underConstruction;

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionMapper transactionMapper,
                                  AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountService = accountService;
    }

    /**
     * 1) The method validates the account, checks account's ownership, makes a transfer
     *  updates the sender's and receiver's balances after a successful transfer otherwise
     *  it will not make a transfer if the:
     *   -sender or receiver is null
     *   -sender and receiver is the same account
     *   -sender has no enough balance
     *   -if both accounts are checking, if not, one of them saving, it needs to be same userId
     * 2)  If the application is under construction or deployment transfer will be blocked. If it
     *   is not under construction a user would be able to make transfer otherwise the application
     *   will throw an exception to block execution and will display a message.
     * @param sender Account
     * @param receiver Account
     * @param amount BigDecimal
     * @param creationDate Date
     * @param message String
     * @return a transaction
     */
    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver,
                                       BigDecimal amount, Date creationDate, String message) {

        if (!underConstruction) {
            validateAccount(sender,receiver);
            checkAccountOwnership(sender,receiver);
            executeBalanceAndUpdateIfRequired(amount,sender,receiver);
            /*
            after all validations are completed, and money is transferred,
            you need to  create Transaction object and save/return it.
             */
            TransactionDTO transactionDTO = new TransactionDTO(sender,receiver,amount,message,creationDate);
            transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));
            return transactionDTO;

        }else {
            throw new UnderConstructionException("App is under construction,try again later.");
        }
    }

    /**
     * makes a transfer if the sender's balance is more than the amount needed to
     * transfer, otherwise it throws exception
     * When a transfer is made, both the senders and receiver's balance must be updated
     * @param amount amount of transfer
     * @param sender account
     * @param receiver account
     */
    private void executeBalanceAndUpdateIfRequired(BigDecimal amount,
                                                   AccountDTO sender, AccountDTO receiver) {

        //if the balance is within the required limit, allows transfer, update the balances
        if(checkSenderBalance(sender, amount)) {
            //make balance transfer between sender and receiver, make the update
           sender.setBalance(sender.getBalance().subtract(amount));//updating when an amount is subtracted
           receiver.setBalance(receiver.getBalance().add(amount));//updating when an amount is added

            //find the sender account
            AccountDTO senderAccount = accountService.retrieveById(sender.getId());
            senderAccount.setBalance(sender.getBalance());
            //save again to database
            accountService.updateAccount(senderAccount);

            //find the receiver account
            AccountDTO receiverAccount = accountService.retrieveById(receiver.getId());
            receiverAccount.setBalance(receiver.getBalance());
            accountService.updateAccount(receiverAccount);
        } else {
            //throw BalanceNotSufficientException, if the balance is not within the limit
            throw new BalanceNotSufficientException("Balance is not enough for this transfer.");
        }
    }

    /**
     * Verifies if the sender has enough balance to send or not
     * NB. Since the balance is BigDecimal type, BigDecimal has subtract()
     * and compareTo() methods to work with numbers to subtract an compare two numbers
     * @param sender Account
     * @param amount Account
     * @return boolean if the sender and receiver are equal or not
     */
    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        //sender balance is less than zero will return false
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * checks if one of the account is saving
     * throws an exception if the sender and receiver is the same
     * @param sender Account
     * @param receiver Account
     */
    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
     //checks if the account is saving, the sender or receiver is not the same, otherwise throws exception
        if ((sender.getAccountType().equals(AccountType.SAVING)||
                receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId())) {
            throw new AccountOwnershipException("Since you are using a savings account, " +
                    "the sender and receiver userId must be the same.");
        }
    }

    /**
     * This a helper method for the makeTransfer() method
     * Trows exception if the:
     *    -if any of the account is null
     *    -if account ids are of the same account
     *    -if the accounts exist in the database(repository)
     * @param sender Account
     * @param receiver Account
     */
    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
        //validate if any of the account is null
        if(sender==null||receiver==null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        //validate if the accounts are the same
        if(sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver");
        }

        //verify if the sender and receiver exist in the database (repository)
        findAccountById(sender.getId());
        findAccountById(receiver.getId());
    }

    /**
     * Finds an account by its id
     * @param id Long
     */
    private AccountDTO findAccountById(Long id) {
        return accountService.retrieveById(id);
    }

    /**
     * finds and returns a list of transactions
     * @return list of transactions
     */
    @Override
    public List<TransactionDTO> findAllTransactions() {

        //get the transaction entity for all and return them as a list of transactionDTO
        return transactionRepository.findAll().stream()
                .map(transactionMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> last10Transactions() {
        //we want last 10 latest transaction
        return transactionRepository.findLast10Transactions().stream()
                .map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListByID(Long id) {
        return transactionRepository.findTransactionListId(id).stream()
                .map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }


}
