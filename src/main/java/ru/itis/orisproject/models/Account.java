package ru.itis.orisproject.models;

public record Account(String username, String password, String email, String icon_path) {
    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String icon_path() {
        return icon_path;
    }
}
