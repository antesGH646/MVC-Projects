package com.cydeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    //1st error: this method was missing, not the home page displays
    @RequestMapping("/home")
    public String homePage() {
        return "home";
    }
}
