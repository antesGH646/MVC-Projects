package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       //get our own user from the db,
       // so first must create UserRepository with a method that fetches you own user
           User user = userRepository.findUserByUsername(username);
        //return an exception if the specified user don't exist in the db
        if(user==null){
            throw new UsernameNotFoundException("This user does not exist");
        }
        //return user information as a UserDetails,
        // our own user entity is mapped through UserPrincipal class
        return new UserPrincipal(user);
    }
}
