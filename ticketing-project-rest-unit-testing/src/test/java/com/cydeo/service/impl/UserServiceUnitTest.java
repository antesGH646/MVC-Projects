package com.cydeo.service.impl;


import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private TaskService taskService;

    @Mock
    private KeycloakService keycloakService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Spy
    private UserMapper userMapper = new UserMapper(new ModelMapper());

    User user;
    UserDTO userDTO;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("user");
        user.setPassWord("Abc1");
        user.setEnabled(true);
        user.setRole(new Role("Manager"));

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setUserName("user");
        userDTO.setPassWord("Abc1");
        userDTO.setEnabled(true);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setDescription("Manager");

        userDTO.setRole(roleDTO);
    }

    private List<User> getUsers(){
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Emily");

        return List.of(user,user2);
    }

    private List<UserDTO> getUserDTOs(){
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setFirstName("Emily");

        return List.of(userDTO,userDTO2);
    }

    @Test
    void shouldListAllUsers_test() {
        //stubbing is used when the returning is used
        //means: whenever this method is called then return this
        when(userRepository.findAllByIsDeletedOrderByFirstNameDesc(false)).thenReturn(getUsers());

        List<UserDTO> expectedList = userService.listAllUsers();

        List<UserDTO> actualList = userService.listAllUsers();
        //verify if they are equal, AssertEquals verify the location not the content
       // Assertions.assertEquals(expectedList, actualList);//fails b/c comparing location

        //To solve this issue use AssertJ library
        assertThat(actualList).usingRecursiveComparison().isEqualTo(expectedList);
        //can ignore null values
        assertThat(actualList).usingRecursiveComparison().ignoringActualNullFields().isEqualTo(expectedList);
    }

    @Test
    void should_find_user_by_userName() {
        //if you use one argument matchers, the other parameter must be also argument matcher, otherwise will fail
        //when(userRepository.findByUserNameAndIsDeleted(anyString(),false)).thenReturn(user);

        //both parameters are argument matchers, so it will pass, if you are stubbing must call the methods too
        //or use lenient() in the front, to prevent is from complaining => lenient().when(...)
        when(userRepository.findByUserNameAndIsDeleted(anyString(),anyBoolean())).thenReturn(user);
           userService.deleteByUserName("user");//calling a method, b/c you are using stabbing
           UserDTO actualUserDTO = userService.findByUserName("user");
           UserDTO expectedUserDTO = userDTO;
           //use JUnit5 assertions
        assertThat(actualUserDTO).usingRecursiveComparison().isEqualTo(expectedUserDTO);
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        //First option: directly returning null when the method is called
     //   lenient().when(userRepository.findByUserNameAndIsDeleted(anyString(),anyBoolean())).thenReturn(null);

        //Option 2: catching the exception when the method is called
        Throwable throwable = catchThrowable(() -> userService.findByUserName("user"));
        //assert the if the thrown exception type
        assertInstanceOf(NoSuchElementException.class,throwable);
        assertEquals("User not found", throwable.getMessage());
    }

    @Test
    void should_save_user() {
        //whenever the encode() and the save() methods are called
        // verify that if userCreate() method is called or not
        when(passwordEncoder.encode(anyString())).thenReturn("Abc1");
        when(userRepository.save(any())).thenReturn(user);
        UserDTO actualDTO = userService.save(userDTO);//calling the method
        verify(keycloakService).userCreate(any());//verify if the userCreate() method is called or not
        assertThat(actualDTO).usingRecursiveComparison().isEqualTo(userDTO);
    }
}
