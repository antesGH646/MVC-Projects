package com.cydeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    //login endpoint created localhost:8080/login or localhost:8080/
    @RequestMapping(value = {"/login","/"})
    public String login(){
        return "login";
    }

    //directed to the welcome endpoint after the login button is clicked (th:action="@{/welcome}")
    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}