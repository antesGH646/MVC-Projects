package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional//must add it, when using ddl operations & derived query methods
public class UserServiceImpl implements UserService {

    //declare the repositories to call methods that execute certain queries
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    //injection through constructor
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    //this method is need to display all the users on the UI table
    @Override
    public List<UserDTO> listAllUsers() {
        //get all the users
        List<User> userList = userRepository.findAll();
        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUserName(username);
        return userMapper.convertToDto(user);
    }

    @Override
    public void save(UserDTO userDTO) {
        userRepository.save(userMapper.convertToEntity(userDTO));
    }

    /**
     * When you are updating, if you do not capture and keep the id,
     * it will create a new record but will not update it.
     * To save UI modified objects or DTO objects in the database you need to
     * convert them into entity objects
     * You need to capture the ID of the object and assign it to the converted entity
     * At last save the converted object.
     * @param userDTO UserDTO object
     * @return UserDTO object to display it again on the UI form. Every UI displayed object
     * is DTO object
     */
    @Override
    public UserDTO update(UserDTO userDTO) {
        //to get the id of the current user, first capture the dto/user by the username
        User user = userRepository.findByUserName(userDTO.getUserName());
        //converting the dto object to entity object, otherwise cannot save it in the db
        User dtoConvertedToEntity = userMapper.convertToEntity(userDTO);
        //setting the id of the captured object, now it will update, but won't duplicate
        dtoConvertedToEntity.setId(user.getId());
        //saving the updated or the converted object user
        userRepository.save(dtoConvertedToEntity);
        //return the dto object capturing it by username
        return findByUserName(userDTO.getUserName());
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> userList = userRepository.findAllByRoleDescriptionIgnoreCase(role);
        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(String username) {
        //don't want to delete from the database, only change the flag in the db
        User user = userRepository.findByUserName(username);
        user.setIsDeleted(true);//the flag is concatenated
        userRepository.save(user);
    }
}
