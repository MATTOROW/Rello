package ru.itis.orisproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.response.SubtaskResponse;
import ru.itis.orisproject.models.TaskEntity;
import ru.itis.orisproject.services.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Извлекаем taskId из сессии
        String taskId = (String) req.getSession().getAttribute("taskId");

        if (taskId != null) {
            TaskService service = (TaskService) getServletContext().getAttribute("TaskService");
            TaskEntity task = service.getEntityById(UUID.fromString(taskId));
            List<SubtaskResponse> subtasks = task.getSubtasks();
            req.setAttribute("task", task);
            req.setAttribute("subtasks", subtasks);
            // Перенаправляем на страницу с деталями задачи
            req.getRequestDispatcher("/WEB-INF/views/task.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task ID is required");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subtaskId = req.getParameter("subtaskId");

        if (subtaskId != null) {
            // Сохраняем subtaskId в сессии
            req.getSession().setAttribute("subtaskId", subtaskId);

            // Перенаправляем на страницу подзадачи
            resp.sendRedirect(req.getContextPath() + "/subtask");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Subtask ID is required");
        }
    }
}
