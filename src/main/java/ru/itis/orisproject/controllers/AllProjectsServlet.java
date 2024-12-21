package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.ProjectResponse;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.ProjectService;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/projects", "/projects/create"})
public class AllProjectsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.equals("/projects")) {
            ProjectService service = (ProjectService) getServletContext().getAttribute("ProjectService");
            AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
            List<ProjectResponse> projects = service.getByUsername(account.getUsername());
            req.setAttribute("projects", projects);
            req.getRequestDispatcher("/WEB-INF/views/all_projects.jsp").forward(req, resp);
        } else if (servletPath.equals("/projects/create")) {
            req.getRequestDispatcher("/WEB-INF/views/create_project.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        ProjectService service = (ProjectService) getServletContext().getAttribute("ProjectService");
        if (servletPath.equals("/projects")) {

        } else if (servletPath.equals("/projects/create")) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
            ProjectRequest projectRequest = new ProjectRequest(name, description);
            service.save(projectRequest, account.getUsername());
            resp.sendRedirect(getServletContext().getContextPath() + "/projects");
        }
    }
}
