package com.cydeo.controller;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.Transaction;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    //injecting by constructor
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    /**
     *
     * @param model Model to carry
     * @return the make-transfer html file under the transaction package to display it in the UI
     */
    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model) {
        //create model attribute that carries empty Transaction object
        model.addAttribute("transaction", Transaction.builder().build());
        //create model attribute that carries list of all accounts
        model.addAttribute("accounts", accountService.listAllAccounts());
        //create model attribute that carries the last 10 list of transactions in sorted order
        model.addAttribute("lastTransactions", transactionService.last10Transactions());
        return "/transaction/make-transfer";
    }

    /**
     * This method takes Transaction object, retrieves a transaction by id
     * then makes the transfer according the specific id
     * @param transaction Transaction
     * @return make-transfer html to display it in the UI
     */
    @PostMapping("/transfer")
    public String makeTransfer(@ModelAttribute("transaction") @Valid Transaction transaction,
                               BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            model.addAttribute("accounts",accountService.listAllAccounts());
            return "/transaction/make-transfer";
        }


        AccountDTO sender = accountService.retrieveAccountById(transaction.getSender());
        AccountDTO receiver = accountService.retrieveAccountById(transaction.getReceiver());
        transactionService.makeTransfer(sender,receiver,transaction.getAmount(),
                new Date(),transaction.getMessage());
        return "redirect:/make-transfer";
    }

    /**
     * This method gets the account id from the UI user entry
     * @param id UUID
     * @param model Model
     * @return the transactions html view under the transaction folder
     */
    @GetMapping("/transaction/{id}")
    public String transaction(@PathVariable("id") UUID id, Model model) {
        System.out.println(id);
        model.addAttribute("transactions", transactionService.findTransactionListByID(id));
        return "/transaction/transactions";
    }

}
