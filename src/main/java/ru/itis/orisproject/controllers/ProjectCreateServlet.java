package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.ProjectService;

import java.io.IOException;

@WebServlet("/project/create")
public class ProjectCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/create_project.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        ProjectService service = (ProjectService) getServletContext().getAttribute("ProjectService");
        AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
        ProjectRequest projectRequest = new ProjectRequest(name, description);
        service.save(projectRequest, account.getUsername());
        resp.sendRedirect(getServletContext().getContextPath() + "/projects");
    }
}
