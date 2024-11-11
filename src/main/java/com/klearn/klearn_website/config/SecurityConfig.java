package com.klearn.klearn_website.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.klearn.klearn_website.security.CustomOAuth2LoginSuccessHandler;
import com.klearn.klearn_website.security.OAuth2AuthenticationFailureHandler;
import com.klearn.klearn_website.security.JwtAuthenticationEntryPoint;
import com.klearn.klearn_website.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
@SpringBootApplication
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;
    private final CustomOAuth2LoginSuccessHandler customOAuth2LoginSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(
                        "/", 
                        "/login", 
                        "/profile", 
                        "/oauth2/**",
                        "/api/homepage/topic-section",
                        "/api/upload",
                        "/api/homepage/vocabulary/**",
                        "/api/auth/**",
                        "/api/course",
                        "/api/course/{courseId}",
                        "/api/vocabulary_topic/**",
                        "/api/vocabulary/**",
                        "/api/grammar/**",
                        "/api/user/reset-password-token/**",
                        "/api/user/reset-password-auth/**"
                    ).permitAll();

                    registry.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    // registry.requestMatchers("/api/mycourse/**").authenticated();
                    registry.anyRequest().authenticated();
                })
                .oauth2Login(oauth2login -> oauth2login
                        .successHandler(customOAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .getOrBuild();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
