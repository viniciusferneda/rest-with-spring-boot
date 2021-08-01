package com.vferneda.restwithspringboot.person.services;

import com.vferneda.restwithspringboot.person.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var user = repository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + userName + " not found!");
        } else {
            return user;
        }
    }
}
