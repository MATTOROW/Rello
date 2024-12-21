package ru.itis.orisproject.dto.response;

import java.sql.Date;

public record TaskResponse(String name, String description, String status, Date startDate, Date endDate) {
}
