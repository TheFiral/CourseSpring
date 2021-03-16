package com.domain;

public enum Role {
    DIRECTOR("DIR","345"),
    HEAD("HEAD","234"),
    SPECIALIST("SPEC","123");

    private String login;
    private String password;

    Role(String login, String password) {
        this.login=login;
        this.password=password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
