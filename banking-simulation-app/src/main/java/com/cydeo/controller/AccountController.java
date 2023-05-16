package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class AccountController {
    //define a service, but need to create a constructor too
    private final AccountService accountService;//to use methods in account service

    //inject accountService object using a constructor
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndex(Model model) {
        //want to display a list of accounts on the UI
        model.addAttribute("accountList", accountService.listAllAccounts());
        //returns the index html file located  under the templates sub-folder
        return "account/index";
    }

    @GetMapping("/create-account")
    public String addNewUserForm(Model model){
        //want to display Account objects into the UI
        model.addAttribute("account", Account.builder().build());
        //want to display a list of the account types from enum into the Account Type dropdown
        model.addAttribute("accountTypes", AccountType.values());
        return "account/create-account";
    }

    /**
     * This Controller method captures a data entry from the UI and displays/populates
     * it into the table. accountService object calls a method to capture the UI data
     * into the java objects. The data is carried through model attribute into
     * the HTML of the table.
     * redirect: means no need to create model attribute => Model model
     * model.addAttribute("accountList", accountService.listAllAccount())
     * The @Valid annotation should be before the model Java object that
     * you need to validate, but it should not be before or after the BindingResult object
     * @param account Account
     * @return the index html file to display filled out table with a captured data
     * that comes from UI form.
     */
    @PostMapping("/create") //create method to capture data from UI
    public String createAccount(@ModelAttribute("account") @Valid Account account,
                                BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("accountTypes", AccountType.values());
            //return it to the same page until the user input is right
            return "account/create-account";
        }
       // System.out.println(account);//print them on the console.
        //triggers the createAccount() method, creates the account based on the user input,
        // will store whatever the user enters.
        accountService.createNewAccount(account.getBalance(),
                account.getCreationDate(),account.getAccountType(), account.getUserId());
        return "redirect:/index";
    }

    /**
     * gets the account id from the UI and provides it to the controller
     * once the id is sent to the controller, you need to find the id that
     * belongs to the account, then update the account status in the html,
     * active to deleted and vice-versa
     * Catches the account id from the html and provides it to the controller
     * @param id UUID
     * @return return the index html displaying the status to deleted or activated
     */
    @GetMapping("/delete/{id}")
        public String deleteAccount(@PathVariable("id") UUID id) {
        System.out.println(id);
        //triggers deleteAccount() method, captures whatever id the user enters
        accountService.deleteAccount(id);
        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id") UUID id) {
        System.out.println(id);
        //trigger deleteAccount method,
        accountService.activateAccount(id);
        return "redirect:/index";
    }
}
