package ru.itis.orisproject.dto.request;

import java.sql.Date;

public record TaskRequest(String name, String description, Date startDate) {
}
