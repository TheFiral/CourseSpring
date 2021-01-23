package com.domain;


public class User {
    private String name;
    private String password;
    private String role;

    private User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = String.valueOf(role);
    }

    private User() {
    }

    public static User Director = new User("Dir", "987", Role.DIRECTOR);
    public static User Master = new User("Head", "654", Role.HEAD);
    public static User user = new User("Spec", "123", Role.SPECIALIST);

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

}
