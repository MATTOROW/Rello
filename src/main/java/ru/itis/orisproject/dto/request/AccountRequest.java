package ru.itis.orisproject.dto.request;

public record AccountRequest(String username, String password, String email, String iconPath, String description) {
}
