package com.klearn.klearn_website.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
  @Value("${app.jwt-secret}")
  private String jwt_secret;


  // generate JWT token
  public String generateToken(Authentication authentication) {
    String username = authentication.getName();

    String token = Jwts.builder()
              .setSubject(username)
              .setIssuedAt(new Date())
              .signWith(key())
              .compact();
    
    return token;
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwt_secret));
  }

// get username from JWT token
public String getUsername(String token){
  return Jwts.parserBuilder()
             .setSigningKey((SecretKey) key())
             .build()
             .parseClaimsJws(token)
             .getBody()
             .getSubject();
}

// validate JWT token
public boolean validateToken(String token) {
  try {
      Jwts.parserBuilder()
          .setSigningKey((SecretKey) key())
          .build()
          .parseClaimsJws(token);
      return true;
  } catch (Exception e) {
      // handle different exceptions for token expiration, signature mismatch, etc.
      return false;
  }
}
}