package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AccountController {
    //defining with final requires to create a constructor too
    private final AccountService accountService;//to use methods in account service

    //inject accountService object using a constructor
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndex(Model model) {
        //want to display list of accounts on the UI
        model.addAttribute("accountList", accountService.listAllAccounts());
        //returns the index html file found in the accounts sub-folder under the templates
        return "account/index";
    }

    @GetMapping("/create-account")
    public String addNewUserForm(Model model){
        //want to display data from Account object into the UI
        model.addAttribute("account", new AccountDTO());
        //want to display a list of the account types from enum into the Account Type dropdown
        model.addAttribute("accountTypes", AccountType.values());
        return "account/create-account";
    }

    /**
     * This Controller method captures a data entry from the UI and displays it
     * into the table.
     * accountService object calls a method that captures the UI data
     * providing the java objects.
     * Through model attribute the data is carried into the HTML of the table,
     * and then through the Thymeleaf the data is displayed into the table.
     * redirect: means no need to create model attribute => Model model
     * model.addAttribute("accountList", accountService.listAllAccount())
     * The @Valid annotation should before the model Java object you need to validate,
     * not before or after the BindingResult object
     * @param accountDTO Account
     * @return the index html file to display a table filled out with data from
     * the added new user form.
     */
    @PostMapping("/create") //create method to capture information from UI
    public String createAccount(@ModelAttribute("account") @Valid AccountDTO accountDTO,
                                BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("accountTypes", AccountType.values());
            return "account/create-account"; //return it to the same page until the user input is right
        }

        System.out.println(accountDTO);//print them on the console.
        //trigger createAccount method, creates the account based on user input,
        // will store whatever the user is entering.
        accountService.createNewAccount(accountDTO);
        return "redirect:/index";
    }

    /**
     * gets the account id from the UI and provide it the controller
     * once the id is sent to the controller, you need to find the id that
     * belongs to the account, then update the account status in the html
     * active to deleted and vice-versa
     * To catch the account id from the html and provide it to the controller
     * @param id UUID
     * @return return the index html displaying the status to deleted or activated
     */
    @GetMapping("/delete/{id}")
        public String deleteAccount(@PathVariable("id") Long id) {
        System.out.println(id);
        //trigger deleteAccount method, captures whatever id the user is entering in the UI
        accountService.deleteAccount(id);
        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id") Long id) {
        System.out.println(id);
        //trigger deleteAccount method,
        accountService.activateAccount(id);
        return "redirect:/index";
    }
}
