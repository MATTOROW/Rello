package ru.itis.orisproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.services.SubtaskService;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/subtask/update-status")
public class UpdateSubtaskStatusServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> requestData = objectMapper.readValue(req.getInputStream(), Map.class);
        String subtaskId = requestData.get("subtaskId");
        boolean completed = Boolean.parseBoolean(requestData.get("completed"));
        String taskId = requestData.get("taskId");

        // Логика обновления статуса подзадачи в базе данных
        SubtaskService subtaskService = (SubtaskService) getServletContext().getAttribute("SubtaskService");
        int success = subtaskService.updateStatus(UUID.fromString(subtaskId), completed, UUID.fromString(taskId));

        if (success == 1) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
