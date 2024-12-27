package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.response.ProjectResponse;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.ProjectService;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/projects")
public class AllProjectsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectService service = (ProjectService) getServletContext().getAttribute("ProjectService");
        AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
        List<ProjectResponse> projects = service.getByUsername(account.getUsername());
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/WEB-INF/views/all_projects.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectId = req.getParameter("projectId");

        if (projectId != null) {
            req.getSession().setAttribute("projectId", projectId);
            resp.sendRedirect(req.getContextPath() + "/project");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
        }
    }
}
