package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {
    //Want information from Postman then add it in the keycloak through the Response class
    Response userCreate(UserDTO userDTO);
    //delete a user from the keycloak
    void delete(String userName);
}
