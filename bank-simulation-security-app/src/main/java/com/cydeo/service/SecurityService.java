package com.cydeo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * To override the loadUserByUsername() method, this interface extends
 * the UserDetailsService. The loadUserByUsername() method returns the UserDetails.
 */
public interface SecurityService extends UserDetailsService {
}
