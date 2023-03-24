package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import com.cydeo.service.impl.RoleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This Controller directs to the creat html inside the user folder
 * The User Creat page is displayed, then user entry is populated into
 * the User List table.
 * GetMapping() to get data from the User Create form
 * PostMapping to populate data into the User List table
 */
@Controller
@RequestMapping("/user")
public class UserController {

    RoleService roleService;
    UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createUser(Model model){

        model.addAttribute("user",new UserDTO());
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("users",userService.findAll());

        return "/user/create";
    }

    /**
     * Both the GetMapping() and the PostMapping() have same end point (user/create)
     * This method adds a saved user into the User List table
     * To populate the roles, user list table, and the user form you need three
     * model attributes
     * the "user" model attribute carries the data of the new UserDTO
     * the "roles" model attribute carries the data of the list of roles
     * the "users" model attribute carries the data of the list of users
     * @param user UserDTO
     * @param model Model
     * @return
     */
    @PostMapping("/create")//posting data from UI entry into the User List table
    public String insertUser(@ModelAttribute("user") UserDTO user, Model model){
        //to get an empty form after clicking save button
        model.addAttribute("user",new UserDTO());
        model.addAttribute("roles",roleService.findAll());
        //save() method accepts UserDTO object as an argument
        userService.save(user);//calling the save() method to add the user
        model.addAttribute("users",userService.findAll());
        return "redirect:/user/create";
    }














}
