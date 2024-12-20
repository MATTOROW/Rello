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

@WebServlet("/projects/*")
public class AccountProjectsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            ProjectService service = (ProjectService) getServletContext().getAttribute("ProjectService");
            AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
            List<ProjectResponse> projects = service.getByUsername(account.getUsername());
            req.setAttribute("projects", projects);
            req.getRequestDispatcher("/WEB-INF/views/projects.jsp").forward(req, resp);
        } else if (pathInfo.equals("/create")) {
            try {
                req.getRequestDispatcher("/WEB-INF/views/create_project.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        ProjectService service = (ProjectService) getServletContext().getAttribute("ProjectService");
        if (pathInfo == null) {

        } else if (pathInfo.equals("/create")) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
            ProjectRequest projectRequest = new ProjectRequest(name, description);
            service.save(projectRequest, account.getUsername());
            resp.sendRedirect(getServletContext().getContextPath() + "/");
        }
    }
}
