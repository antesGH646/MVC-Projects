package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /**
     * wants to display the user objects/fields, roles, and users in the view, these objects
     * will be integrated into the create.html file found in the user sub-folder
     * To carry the attributes into the html file, you need to create a Model attribute and add the
     * objects into it using the addAttribute() method
     * @param model Model
     * @return String the create.html file when it is called by the /user/create endpoint
     */
    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("users", userService.findAll());
        return "user/create";
    }

    /**
     * Both the GetMapping() and the PostMapping() have same end point (user/create)
     * This method adds a saved user into the User List table
     * To populate the roles, user object, and the users into the view or UI you need to
     * create a model attribute and add the objects.
     *  -the "user" model attribute carries the data of the new UserDTO
     *  -the "roles" model attribute carries the data of the list of roles
     *  -the "users" model attribute carries the data of the list of users
     * @param user  UserDTO, instead of writing model.addAttribute("user", new UserDTO()),
     *              as an alternative you may write @ModelAttribute("user")
     * @param model Model
     * @return String, returns the create.html file that displays the attributes or
     * objects that come from java which were integrated using the thymeleaf syntax
     * inside the html file.
     */
    @PostMapping("/create")//posting data from UI entry into the User List table
    public String insertUser(@ModelAttribute("user") UserDTO user, Model model) {
        //save() method accepts UserDTO object as an argument
        userService.save(user);//calling the save() method to add the user
        return "redirect:/user/create";//redirecting instead of rewriting the 3 attributes
    }

    /**
     * When you want to update you need to be able to enter data into the table first
     * After you enter the data, the data is retrieved and populated into the form. When
     * the save button is clicked the data is saved in the table. @PathVariable is used to catch
     * When working with browser can only use either Get or Post
     * username is the unique id to grab the line
     * Need to define the attributes, the java class objects (users), roles, and fields.
     * The form should not be empty, it must be the filled out form
     * data from browser
     * @return String
     */
    @GetMapping("/update/{username}")//username end point
    public String editUser(@PathVariable("username") String username,Model model){

      //  userService.update(u);
        //define attributes: display a filled out form, not a new form => "user", new UserDTO()
        model.addAttribute("user",userService.findById(username));
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("users",userService.findAll());
        return "user/update";
    }

    /**
     * Whenever the update button is clicked the data must be populated into the form.
     * The data is retrieved/caught and filled out into the form. After you click save button
     * in the form then the data is saved in the table.
     * @return String returning the user/create view after saving the update
     */
    @PostMapping("/update")
    public String updateUser(UserDTO user){
        userService.update(user);//updating the map, whatever the user is holding
        return "redirect:/user/create";//can use redirect to avoid rewriting attributes
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username){
        userService.deleteById(username);
        return "redirect:/user/create";
    }
}
