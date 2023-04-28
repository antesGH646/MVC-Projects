package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * controller requests all the roles, therefore go to the database
 * fetch and return all the roles, the repository level gives
 * anything you want from the database
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserDTO> listAllUsers() {

        return null;
    }

    @Override
    public UserDTO findByUserName(String username) {
        return null;
    }

    @Override
    public void save(UserDTO userDTO) {

    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO delete(String username) {
        return null;
    }
}
