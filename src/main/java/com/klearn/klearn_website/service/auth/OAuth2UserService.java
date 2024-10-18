package com.klearn.klearn_website.service.auth;

import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.security.JwtTokenProvider;
import com.klearn.klearn_website.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public String processOAuthPostLogin(String email, OAuth2User oAuth2User) {
        User existingUser = userMapper.findByUsernameOrEmail(email, email);

        if (existingUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(oAuth2User.getAttribute("email"));
            newUser.setFullname(oAuth2User.getAttribute("family_name") + " " + oAuth2User.getAttribute("given_name"));
            newUser.setPassword("");
            newUser.setRole(0);
            newUser.setAvatar(oAuth2User.getAttribute("picture"));
            newUser.setLast_login(LocalDateTime.now());
            newUser.setLast_modified(LocalDateTime.now());
            newUser.setType("email");
            userMapper.createUser(newUser);
        } else {
            userMapper.updateLastLogin(existingUser.getId(), LocalDateTime.now());
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }
}
