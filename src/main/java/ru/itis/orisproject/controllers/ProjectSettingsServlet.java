package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.AccountResponse;
import ru.itis.orisproject.dto.response.ProjectResponse;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.models.ProjectEntity;
import ru.itis.orisproject.services.AccountProjectService;
import ru.itis.orisproject.services.ProjectService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/project/settings")
public class ProjectSettingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получение проекта по projectId из сессии или параметров
        String projectId = (String) req.getSession().getAttribute("projectId");
        if (projectId != null) {
            ProjectService projectService = (ProjectService) getServletContext().getAttribute("ProjectService");
            AccountProjectService accountProjectService = (AccountProjectService) getServletContext()
                    .getAttribute("AccountProjectService");

            // Получаем проект
            ProjectResponse project = projectService.getById(UUID.fromString(projectId));
            req.setAttribute("project", project);

            // Получаем участников проекта и роль пользователя
            AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
            String role = accountProjectService.getRole(UUID.fromString(projectId), account.getUsername());
            req.setAttribute("this_account_role", role);

            AccountResponse owner = accountProjectService.getOwner(UUID.fromString(projectId));
            req.setAttribute("owner", owner);

            req.setAttribute("roles", accountProjectService.getAllRoles());

            Map<AccountResponse, String> participants = accountProjectService.getAllParticipants(UUID.fromString(projectId));
            req.setAttribute("participants", participants);


            req.getRequestDispatcher("/WEB-INF/views/project_settings.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectId = req.getParameter("projectId");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        if (name == null || description == null || name.isEmpty() || description.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name or description is required");
        }

        ProjectService service = (ProjectService) getServletContext().getAttribute("ProjectService");
        int code = service.updateById(UUID.fromString(projectId), new ProjectRequest(name, description));
        if (code == 1) {
            req.setAttribute("message", "Project settings updated successfully.");
        } else {
            req.setAttribute("message", "Project settings update failed.");
        }
        doGet(req, resp);
    }
}
