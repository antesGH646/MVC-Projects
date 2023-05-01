package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User, Long> {

    //creating custom method
    User findByUserName(String username);

    //if you are using the ddl operation must use the @Modify annotation,
    // but when you are using the  derived queries methods must use the @Transactional
    @Transactional
    void deleteByUserName(String username);
}
