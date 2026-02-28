package com.basic.sensor2browser.service;

import com.basic.sensor2browser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security UserDetailsService implementation that loads User entities
 * from the database based on their email address for authentication.
 */
@Service
public class UsersDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Manage UsernameNotFound exception at GlobalExceptionHandler
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
