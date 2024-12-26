package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.models.ProjectEntity;
import ru.itis.orisproject.services.ProjectService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/project")
public class ProjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Извлекаем projectId из сессии
        String projectId = (String) req.getSession().getAttribute("projectId");

        if (projectId != null) {
            ProjectService service = (ProjectService) getServletContext().getAttribute("ProjectService");
            ProjectEntity project = service.getEntityById(UUID.fromString(projectId));
            List<TaskResponse> tasks = project.getTasks();
            req.setAttribute("project", project);
            req.setAttribute("tasks", tasks);

            // Перенаправляем на страницу с деталями проекта
            req.getRequestDispatcher("/WEB-INF/views/project.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskId = req.getParameter("taskId");

        if (taskId != null) {
            // Сохраняем taskId в сессии
            req.getSession().setAttribute("taskId", taskId);

            // Перенаправляем на страницу задачи
            resp.sendRedirect(req.getContextPath() + "/task");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task ID is required");
        }
    }
}
