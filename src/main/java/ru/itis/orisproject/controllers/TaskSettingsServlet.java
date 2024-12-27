package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.request.TaskRequest;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.services.TaskService;

import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

@WebServlet("/task/settings")
public class TaskSettingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskId = (String) req.getSession().getAttribute("taskId");
        if (taskId != null) {
            TaskService taskService = (TaskService) getServletContext().getAttribute("TaskService");

            TaskResponse task = taskService.getById(UUID.fromString(taskId));
            if (task != null) {
                req.setAttribute("task", task);
                req.getRequestDispatcher("/WEB-INF/views/task_settings.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task ID is required");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskService service = (TaskService) getServletContext().getAttribute("TaskService");
        String taskId = req.getParameter("taskId");

        if (req.getParameter("deleteTask") != null) {
            service.deleteById(UUID.fromString(taskId));
            resp.sendRedirect(req.getContextPath() + "/project");
            return;
        }

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String startDate = req.getParameter("startDate");

        if (name == null || description == null
                || name.isEmpty() || description.isEmpty()
                || startDate == null || startDate.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name, start date and description is required");
        }
        int code = service.updateById(UUID.fromString(taskId), new TaskRequest(name, description, Date.valueOf(startDate)));
        if (code == 1) {
            req.setAttribute("message", "Task settings updated successfully.");
        } else {
            req.setAttribute("message", "Task settings update failed.");
        }
        doGet(req, resp);
    }
}
