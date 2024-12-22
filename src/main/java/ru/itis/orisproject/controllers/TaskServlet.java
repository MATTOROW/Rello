package ru.itis.orisproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.services.TaskService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@WebServlet("/tasks/*")
public class TaskServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL");
            return;
        }
        String[] paths = pathInfo.split("/");
        TaskService taskService = (TaskService) getServletContext().getAttribute("TaskService");
        if (paths.length == 2) {
            String projectId = paths[1];
            if (projectId.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
                List<TaskResponse> tasks = taskService.getByProjectId(UUID.fromString(projectId));
                objectMapper.writeValue(resp.getWriter(), tasks);
            } else {
                resp.getWriter().write("Invalid project UUID format.");
            }
        } else if (paths.length == 3) {
            String taskId = paths[2];
            if (taskId.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
                TaskResponse task = taskService.getById(UUID.fromString(taskId));
                objectMapper.writeValue(resp.getWriter(), task);
            } else {
                resp.getWriter().write("Invalid project UUID format.");
            }
        }
    }
}
