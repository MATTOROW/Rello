package ru.itis.orisproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.services.AccountProjectService;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/project/participants/update-role")
public class ParticipantUpdateRoleServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> requestData = objectMapper.readValue(req.getInputStream(), Map.class);
        String projectId = requestData.get("projectId");
        String username = requestData.get("username");
        String role = requestData.get("role");

        // Логика обновления роли пользователя в базе данных
        // Убедитесь, что только OWNER может менять роль и что роль может быть только на ADMIN или MEMBER
        AccountProjectService service = (AccountProjectService) getServletContext().getAttribute("AccountProjectService");
        int code = service.updateRole(UUID.fromString(projectId), username, role);
        if (code == 1) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Failed to update role");
        }
    }
}
