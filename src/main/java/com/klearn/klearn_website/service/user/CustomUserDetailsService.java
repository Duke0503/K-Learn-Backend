package com.klearn.klearn_website.service.user;

import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.model.User;
import lombok.AllArgsConstructor;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {

        User user = userMapper.findByUsernameOrEmail(emailOrUsername, emailOrUsername);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username or email: " + emailOrUsername);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                Collections.emptyList());
    }
}
