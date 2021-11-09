package com.softwaresecuritytesting.softwaresecuritytesting.service;

import com.softwaresecuritytesting.softwaresecuritytesting.model.CustomUserDetails;
import com.softwaresecuritytesting.softwaresecuritytesting.repository.UserRepository;
import com.softwaresecuritytesting.softwaresecuritytesting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

}