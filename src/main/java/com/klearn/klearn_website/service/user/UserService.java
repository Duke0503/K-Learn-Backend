package com.klearn.klearn_website.service.user;

import com.klearn.klearn_website.mapper.UserMapper;
import com.klearn.klearn_website.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  
  private UserMapper userMapper;

  public User getUser(String emailOrUsername) {
    return userMapper.findByUsernameOrEmail(emailOrUsername, emailOrUsername);
  }
}
