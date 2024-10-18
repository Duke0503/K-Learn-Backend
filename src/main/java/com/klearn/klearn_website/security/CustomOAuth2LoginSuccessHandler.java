package com.klearn.klearn_website.security;

import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.model.User;
import com.klearn.klearn_website.service.auth.OAuth2UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CustomOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2UserService oAuth2UserService;
    private final UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        String token = oAuth2UserService.processOAuthPostLogin(email, oAuth2User);

        User user = userMapper.findByUsernameOrEmail(email, email);

        String role;

        switch (user.getRole()) {
            case 0:
                role = "user";
                break;
            case 1:
                role = "admin";
                break;
            default:
                role = "content-management";
        }
        String redirectUrl = String.format(
                "http://localhost:5173/oauth2/redirect?token=%s&role=%s&type=%s",
                java.net.URLEncoder.encode(token, StandardCharsets.UTF_8.name()),
                java.net.URLEncoder.encode(role, StandardCharsets.UTF_8.name()),
                java.net.URLEncoder.encode(user.getType(), StandardCharsets.UTF_8.name()));

        response.sendRedirect(redirectUrl);
    }
}
