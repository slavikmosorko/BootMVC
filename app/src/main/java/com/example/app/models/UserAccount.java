package com.example.app.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class UserAccount extends User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    String id;
    @GeneratedValue
    @Column(name = "int_id", updatable = false, nullable = false)
    long intId;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;
    @Column(name = "activation_code")
    String activationCode;
    @Column(name = "enabled")
    boolean enabled = false;

    public UserAccount(String id,
                       long intId,
                       String username,
                       String password,
                       boolean enabled,
                       boolean accountNonExpired,
                       boolean credentialsNonExpired,
                       boolean accountNonLocked,
                       Collection authorities,
                       String activationCode) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.intId = intId;
        this.username = username;
        this.password = password;
        this.activationCode = activationCode;
        this.enabled = enabled;
    }

    public UserAccount(String username,
                       String password,
                       boolean enabled,
                       boolean accountNonExpired,
                       boolean credentialsNonExpired,
                       boolean accountNonLocked,
                       Collection authorities,
                       String activationCode) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.username = username;
        this.password = password;
        this.activationCode = activationCode;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getIntId() {
        return intId;
    }

    public void setIntId(long intId) {
        this.intId = intId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
