package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService extends CrudService<UserDTO,String> {
    //specific methods for UserService only
    List<UserDTO> findManagers();
    List<UserDTO> findEmployees();
}
