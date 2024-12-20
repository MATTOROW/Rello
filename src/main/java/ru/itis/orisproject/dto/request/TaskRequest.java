package ru.itis.orisproject.dto.request;

import java.sql.Date;

public record TaskRequest(String name, String description, Long projectId, String status, Date startDate, Date endDate) {
}
