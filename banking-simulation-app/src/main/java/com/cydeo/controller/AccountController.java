package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class AccountController {
    //define a service, but need to create a constructor t
    private final AccountService accountService;

    //a constructor
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("accountList", accountService.listAllAccount());
        return "account/index";//returns the index html file from the accounts sub folder under the templates
    }

    @GetMapping("/create-form")
    public String getCreateForm(Model model){
        //empty account object provided
        model.addAttribute("account", Account.builder().build());
        //account type enum needs to fill dropdown
        model.addAttribute("accountTypes", AccountType.values());
        return "account/create-account";
    }

    //create method to capture information from UI,
    //print them on the console.
    //trigger createAccount method, create the account based on user input.

    @PostMapping("/create") //create method to capture information from UI
    public String createAccount(@ModelAttribute("account") Account account) {
        System.out.println(account);
        accountService.createNewAccount(account.getBalance(),
                account.getCreationDate(),account.getAccountType(), account.getUserId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
        public String deleteAccount(@PathVariable("id")UUID id) {
        System.out.println(id);
        //trigger deleteAccount method
        accountService.deleteAccount(id);
        return "redirect:/index";
    }

    @GetMapping("/actvate/{id}")
    public String activateAccount(@PathVariable("id")UUID id) {
        System.out.println(id);
        //trigger deleteAccount method
        accountService.activateAccount(id);
        return "redirect:/index";
    }
}
