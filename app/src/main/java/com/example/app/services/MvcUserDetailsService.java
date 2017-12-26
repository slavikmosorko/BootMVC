package com.example.app.services;

import com.example.app.models.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MvcUserDetailsService extends JdbcDaoSupport implements UserDetailsService {
//    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ILogInService logInService;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserAccount> users = logInService.loadUsersByUsername(username);

        if (users.size() == 0) {
            this.logger.debug("Query returned no results for user '" + username + "'");

            throw new UsernameNotFoundException(
                    "Username " + username + " not found");
        }

        UserAccount user = users.get(0); // contains no GrantedAuthority[]

        List<GrantedAuthority> dbAuths = logInService.loadUserAuthoritiesByUserId(user.getId());

        if (dbAuths.size() == 0) {
            this.logger.debug("User '" + username
                    + "' has no authorities and will be treated as 'not found'");
            throw new UsernameNotFoundException(
                    "User " + username + " has no GrantedAuthority");
        }
        return new UserAccount(user.getId(),
                user.getIntId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                dbAuths,
                user.getActivationCode());
    }
}
