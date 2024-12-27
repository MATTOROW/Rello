package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.request.TaskRequest;
import ru.itis.orisproject.services.TaskService;

import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

@WebServlet("/task/create")
public class TaskCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectId = (String) req.getSession().getAttribute("projectId");
        if (projectId != null) {
            req.setAttribute("projectId", projectId);
            req.getRequestDispatcher("/WEB-INF/views/create_task.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectId = req.getParameter("projectId");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String startDate = req.getParameter("startDate");

        if (projectId != null && name != null && description != null && startDate != null) {
            TaskService taskService = (TaskService) getServletContext().getAttribute("TaskService");
            TaskRequest newTask = new TaskRequest(name, description, Date.valueOf(startDate));
            taskService.save(newTask, UUID.fromString(projectId));
            resp.sendRedirect(req.getContextPath() + "/project");
        }
    }
}
