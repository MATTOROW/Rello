package ru.itis.orisproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.request.AccountRequest;
import ru.itis.orisproject.dto.response.AccountResponse;
import ru.itis.orisproject.services.AccountProjectService;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/project/participants/add")
public class ParticipantAddServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Парсим JSON в Map
            Map<String, String> requestData = objectMapper.readValue(req.getInputStream(), Map.class);
            String projectId = requestData.get("projectId");
            String username = requestData.get("username");
            String role = requestData.get("role");

            if (projectId == null || username == null || role == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Project ID, username and role are required.");
                return;
            }



            // Добавляем участника в проект
            AccountProjectService service = (AccountProjectService) getServletContext().getAttribute("AccountProjectService");
            service.addNewParticipant(UUID.fromString(projectId), username, role);

            // Успешный ответ
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Обработка ошибок
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }
}
