package com.example.app.services;

import com.example.app.models.UserAccount;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface ILogInService {
    List<UserAccount> loadUsersByUsername(String username);
    List<GrantedAuthority> loadUserAuthoritiesByUserId(String userId);
}
