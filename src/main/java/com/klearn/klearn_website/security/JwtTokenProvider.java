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

  @Value("${app.jwt-expiration-milliseconds}")
  private long jwt_expiration_date;

  // generate JWT token
  public String generate_token(Authentication authentication) {
    String username = authentication.getName();

    Date current_date = new Date();

    Date expire_date = new Date(current_date.getTime() + jwt_expiration_date);

    String token = Jwts.builder()
              .subject(username)
              .issuedAt(new Date())
              .expiration(expire_date)
              .signWith(key())
              .compact();
    
    return token;
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwt_secret));
  }

  // get username from JWT token
  public String get_username(String token){

    return Jwts.parser()
              .verifyWith((SecretKey) key())
              .build()
              .parseSignedClaims(token)
              .getPayload()
              .getSubject();
  }

  // validate JWT token
  public boolean validate_token(String token) {
    Jwts.parser()
      .verifyWith((SecretKey) key())
      .build()
      .parse(token);

    return true;
  }
}
