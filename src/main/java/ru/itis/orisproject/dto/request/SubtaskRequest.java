package ru.itis.orisproject.dto.request;

import java.sql.Date;
import java.util.UUID;

public record SubtaskRequest(String name, String description, UUID taskId, boolean completed, Date endDate) {
}
