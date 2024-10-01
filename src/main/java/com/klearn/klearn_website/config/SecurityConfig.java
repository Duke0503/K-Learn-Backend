package com.klearn.klearn_website.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.klearn.klearn_website.security.JwtAuthenticationEntryPoint;
import com.klearn.klearn_website.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authorize) -> {
                // Permit all requests to auth-related endpoints (login, register)
                authorize.requestMatchers("/api/auth/**").permitAll();
                
                // Allow preflight OPTIONS requests for CORS
                authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                
                // All other requests must be authenticated
                authorize.anyRequest().authenticated();
            })
            .httpBasic(Customizer.withDefaults());

        // Handle authentication entry point (for unauthenticated access)
        http.exceptionHandling(exception -> exception
            .authenticationEntryPoint(authenticationEntryPoint));

        // Add the JWT filter to process JWTs in each request
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
