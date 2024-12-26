package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.request.SubtaskRequest;
import ru.itis.orisproject.services.SubtaskService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/subtask/create")
public class SubtaskCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskId = (String) req.getSession().getAttribute("projectId");
        if (taskId != null) {
            // Передаем projectId на страницу создания задачи
            req.setAttribute("taskId", taskId);
            req.getRequestDispatcher("/WEB-INF/views/create_subtask.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task ID is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Извлекаем taskId из сессии
        String taskId = (String) req.getSession().getAttribute("taskId");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        if (taskId != null && name != null && description != null) {
            // Создаем подзадачу
            SubtaskService service = (SubtaskService) getServletContext().getAttribute("SubtaskService");
            SubtaskRequest newSubtask = new SubtaskRequest(name, description);
            service.save(newSubtask, UUID.fromString(taskId));
            // Перенаправляем обратно на страницу задачи
            resp.sendRedirect(req.getContextPath() + "/task");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required");
        }
    }
}
