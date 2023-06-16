package com.cydeo.controller;

import com.cydeo.annotation.DefaultExceptionMessage;
import com.cydeo.annotation.ExecutionTime;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name="UserController",description = "User API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Return a list of all users
     * @return ResponseWrapper
     */
    @ExecutionTime//monitors the performance time through Performance aspect
    @GetMapping
    @RolesAllowed("Admin")//adding restriction based on method level
    @Operation(summary = "Get Users")
    public ResponseEntity<ResponseWrapper> getUsers() {
        //getting the list from UserService
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("User are successfully retrieved",userDTOList, HttpStatus.OK));
    }

    /**
     * Return a user by username
     * the response code is the json payload or body code
     * not the status code
     * @return ResponseWrapper
     */
    @ExecutionTime//monitors the execution time through Performance aspect
    @GetMapping("/{userName}")
    @RolesAllowed("Admin")
    @Operation(summary = "Get User By Username ")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable("userName") String userName) {
        //getting the list from UserService
        UserDTO userDTO = userService.findByUserName(userName);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully retrieved",userDTO, HttpStatus.OK));
    }

    /**
     * UserDTO is fetched by PostMan then captured by the UserService
     * @param userDTO user
     * @return ResponseWrapper mapped user object
     */
    @PostMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Create User")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User is successfully created",HttpStatus.CREATED));
    }

    /**
     * UserDTO is fetched by PostMan then captured by the UserService
     * @param userDTO user
     * @return ResponseWrapper mapped user object
     */

    @PutMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Update User")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated",HttpStatus.OK));
    }

    @DeleteMapping("/{userName}")
    @RolesAllowed("Admin")
    @Operation(summary = "Delete User")
    @DefaultExceptionMessage(defaultMessage = "Failed to delete user")//using custom annotation
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("userName") String username) throws TicketingProjectException {
      //  userService.deleteByUserName(username);
        userService.delete(username);
       // return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted",HttpStatus.OK));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("User is successfully deleted",HttpStatus.OK));
    }
}
