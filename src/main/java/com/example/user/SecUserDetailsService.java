package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SecUserDetailsService implements UserDetailsService {

    @Autowired
    private SecUserRepository secUserRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UserEntity userEntity = secUserRepository.findByLogin(login);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Login " + login + " was not found !!!");
        }

        Set<GrantedAuthority> roles = new HashSet<>();

        UserDetails userDetails = new User(userEntity.getLogin(), userEntity.getPassword(), roles);

        return userDetails;
    }

}
