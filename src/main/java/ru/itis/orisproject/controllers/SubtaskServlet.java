package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.models.SubtaskEntity;
import ru.itis.orisproject.services.SubtaskService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/subtask")
public class SubtaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subtaskId = (String) req.getSession().getAttribute("subtaskId");

        if (subtaskId != null) {
            SubtaskService service = (SubtaskService) getServletContext().getAttribute("SubtaskService");
            SubtaskEntity subtask = service.getEntityById(UUID.fromString(subtaskId));
            if (subtask != null) {
                req.setAttribute("subtask", subtask);
                req.getRequestDispatcher("/WEB-INF/views/subtask.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Subtask not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Subtask ID is required");
        }
    }
}