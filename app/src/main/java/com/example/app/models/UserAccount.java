package com.example.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserAccount {
    @Id
    @Column(name = "username")
    String userName;
    @Column(name = "password")
    String password;
    @Column(name = "activation_code")
    String activationCode;
    @Column(name = "enabled")
    boolean enabled = false;

    public UserAccount(String userName, String password, String activationCode) {
        this.userName = userName;
        this.password = password;
        this.activationCode = activationCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
