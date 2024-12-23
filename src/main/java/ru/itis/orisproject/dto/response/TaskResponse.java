package ru.itis.orisproject.dto.response;

import java.sql.Date;
import java.util.UUID;

public record TaskResponse(UUID taskId, String name, String description, String status, Date startDate, Date endDate) {
}
