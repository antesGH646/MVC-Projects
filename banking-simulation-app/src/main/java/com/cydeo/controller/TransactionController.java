package com.cydeo.controller;

import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    public String getMakeTransfer() {
        //make transfer happen
        //provide empty transaction object
        //make all accounts to receive and send
        //display the last 10 list of transactions
        return "transaction/make-transfer";
    }
}
