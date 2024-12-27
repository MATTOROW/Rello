package ru.itis.orisproject.models;

public class AccountEntity {
    private String username;
    private String password;
    private String email;
    private String iconPath;
    private String description;

    public AccountEntity(String username, String password, String email, String iconPath, String description) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.iconPath = iconPath;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
