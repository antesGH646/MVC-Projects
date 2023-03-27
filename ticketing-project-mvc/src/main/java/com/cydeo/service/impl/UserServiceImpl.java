package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Instead of using if-else and for loops the best
     * way is to use Streams (Java update features)
     * put the data in a pipeline, filter it, then put in a list
     * @return UserDTO
     */
    @Override
    public List<UserDTO> findManagers() {
        return super.findAll().stream()
                .filter(p -> p.getRole().getId() == 2)
                .collect(Collectors.toList());
    }
}
