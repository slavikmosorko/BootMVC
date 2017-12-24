package com.example.app.services;

import com.example.app.daos.ILogInDAO;
import com.example.app.models.UserAccount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogInService implements ILogInService {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ILogInDAO logInDAO;

    @Override
    public List<UserAccount> loadUsersByUsername(String username) {
        try {
            return logInDAO.loadUsersByUsername(username)
                    .stream()
                    .map(userAccountDTO ->
                            new UserAccount(userAccountDTO.getId(),
                                    userAccountDTO.getIntId(),
                                    userAccountDTO.getUsername(),
                                    userAccountDTO.getPassword(),
                                    userAccountDTO.isEnabled(),
                                    true,
                                    true,
                                    true,
                                    Collections.EMPTY_LIST,
                                    userAccountDTO.getActivationCode()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to load users with username: " + username);
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<GrantedAuthority> loadUserAuthoritiesByUserId(String userId) {
        try {
            return logInDAO.loadUserAuthoritiesByUserId(userId).stream()
                    .map(grantedAuthorityDTO -> new SimpleGrantedAuthority(grantedAuthorityDTO.getAuthority()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to load authorities for user ID: " + userId);
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}
