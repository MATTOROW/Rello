package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.request.SubtaskRequest;
import ru.itis.orisproject.dto.response.SubtaskResponse;
import ru.itis.orisproject.services.SubtaskService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/subtask/settings")
public class SubtaskSettingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subtaskId = (String) req.getSession().getAttribute("subtaskId");
        if (subtaskId != null) {
            SubtaskService subtaskService = (SubtaskService) getServletContext().getAttribute("SubtaskService");

            SubtaskResponse subtask = subtaskService.getById(UUID.fromString(subtaskId));
            if (subtask != null) {
                req.setAttribute("subtask", subtask);
                req.getRequestDispatcher("/WEB-INF/views/subtask_settings.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Subtask not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Subtask ID is required");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubtaskService service = (SubtaskService) getServletContext().getAttribute("SubtaskService");
        String subtaskId = req.getParameter("subtaskId");

        if (req.getParameter("deleteSubtask") != null) {
            service.deleteById(UUID.fromString(subtaskId));
            resp.sendRedirect(req.getContextPath() + "/task");
            return;
        }

        String name = req.getParameter("name");
        String description = req.getParameter("description");

        if (name == null || description == null || name.isEmpty() || description.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name and description is required");
        }
        int code = service.updateById(UUID.fromString(subtaskId), new SubtaskRequest(name, description));
        if (code == 1) {
            req.setAttribute("message", "Subtask settings updated successfully.");
        } else {
            req.setAttribute("message", "Subtask settings update failed.");
        }
        doGet(req, resp);
    }
}
