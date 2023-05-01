package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {

    //on the service layer always return DTOs
    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO userDTO);//to save the new user
    UserDTO update(UserDTO userDTO);//to update the new user
    void delete(String username);//to delete the new user
    void deleteByUserName(String username);//to delete the new user
}
