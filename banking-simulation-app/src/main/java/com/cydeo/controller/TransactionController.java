package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Transaction;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model) {
        //make transfer happen
        //provide empty transaction object
        model.addAttribute("transaction", Transaction.builder().build());
        //make all accounts to receive and send
        model.addAttribute("accountTypes", AccountType.values());
        //display the last 10 list of transactions
        return "transaction/make-transfer";
    }
}
