package com.example.app.daos;

import com.example.app.models.GrantedAuthorityDTO;
import com.example.app.models.UserAccountDTO;

import java.util.List;

public interface ILogInDAO {
    List<UserAccountDTO> loadUsersByUsername(String username);

    List<GrantedAuthorityDTO> loadUserAuthoritiesByUserId(String userId);
}
