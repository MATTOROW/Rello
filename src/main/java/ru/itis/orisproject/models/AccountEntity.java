package ru.itis.orisproject.models;

public class AccountEntity {
    private String username;
    private String password;
    private String email;
    private String iconPath;

    public AccountEntity(String username, String password, String email, String iconPath) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.iconPath = iconPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}