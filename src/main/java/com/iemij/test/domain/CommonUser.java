package com.iemij.test.domain;

import javax.persistence.Id;
import javax.persistence.Table;

@Table( name = "common_user")
public class CommonUser {
    @Id
    private Long uid;
    private String account;
    private String password;
    private String username;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
