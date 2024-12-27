package ru.itis.orisproject.dto.response;

import java.util.UUID;

public record ProjectResponse(UUID projectId, String name, String description) {
}
