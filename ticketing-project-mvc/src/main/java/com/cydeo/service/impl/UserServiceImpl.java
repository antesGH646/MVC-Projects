package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//used with services; it combines @Component, @Retention, @Documented
public class UserServiceImpl extends AbstractMapService<UserDTO,String> implements UserService {

    //adds a user into the map and returns the added user
    @Override
    public UserDTO save(UserDTO object) {
        return super.save(object.getUserName(),object);
    }

    //stores a list of users and returns the list of users
    @Override
    public List<UserDTO> findAll() {
        return super.findAll();
    }

    //deletes a user by id, and returns the deleted user
    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    //finds a user by id and returns the found user
    @Override
    public UserDTO findById(String id) {
        return super.findById(id);
    }

    @Override
    public void update(UserDTO user) {
        super.update(user.getUserName(),user);
    }
}
