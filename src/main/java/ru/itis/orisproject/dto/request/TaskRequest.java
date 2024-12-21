package ru.itis.orisproject.dto.request;

import java.sql.Date;
import java.util.UUID;

public record TaskRequest(String name, String description, UUID projectId, String status, Date startDate, Date endDate) {
}
