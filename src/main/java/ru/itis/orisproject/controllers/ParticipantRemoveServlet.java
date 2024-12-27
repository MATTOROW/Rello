package ru.itis.orisproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.AccountProjectService;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/project/participants/remove")
public class ParticipantRemoveServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> requestData = objectMapper.readValue(req.getInputStream(), Map.class);
        String projectId = requestData.get("projectId");
        String usernameToRemove = requestData.get("username");
        if (projectId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is missing.");
            return;
        }
        AccountProjectService accountProjectService = (AccountProjectService) getServletContext().getAttribute("AccountProjectService");
        AccountEntity currentUser = (AccountEntity) req.getSession().getAttribute("account");
        String userRole = accountProjectService.getRole(UUID.fromString(projectId), currentUser.getUsername());

        if (!"OWNER".equals(userRole) && !"ADMIN".equals(userRole)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to remove members.");
            return;
        }

        if (usernameToRemove == null || usernameToRemove.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username is required.");
            return;
        }

        String targetRole = accountProjectService.getRole(UUID.fromString(projectId), usernameToRemove);

        if ("OWNER".equals(targetRole)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Cannot remove the owner of the project.");
            return;
        }

        if ("ADMIN".equals(userRole) && "ADMIN".equals(targetRole)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Admins cannot remove other admins.");
            return;
        }

        accountProjectService.deleteParticipant(UUID.fromString(projectId), usernameToRemove);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
