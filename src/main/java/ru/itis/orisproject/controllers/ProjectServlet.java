package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.response.AccountResponse;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.AccountProjectService;
import ru.itis.orisproject.services.ProjectService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/projects/*")
public class ProjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        boolean hasErrors = false;
        if (pathInfo.split("/").length == 2) {
            if (pathInfo != null && pathInfo.startsWith("/")) {
                String uuid = pathInfo.substring(1);  // Извлекаем uuid (удаляем ведущий "/")
                if (uuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
                    AccountProjectService accountProjectService = (AccountProjectService) getServletContext()
                            .getAttribute("AccountProjectService");
                    AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
                    if (accountProjectService.hasAccess(UUID.fromString(uuid), account.getUsername())) {
                        ProjectService projectService = (ProjectService) getServletContext().getAttribute("ProjectService");
                        req.setAttribute("project", projectService.getEntityById(UUID.fromString(uuid)));
                        req.getRequestDispatcher("/WEB-INF/views/project.jsp").forward(req, resp);
                    } else {
                        AccountResponse owner = accountProjectService.getOwner(UUID.fromString(uuid));
                        resp.getWriter().write(
                                "<p>You have no access to this project. Please contact %s. His email %s</p>"
                                .formatted(owner.username(), owner.email())
                        );
                    }
                } else {
                    hasErrors = true;
                }
            } else {
                hasErrors = true;
            }

            if (hasErrors) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid project UUID format.");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("The requested resource was not found");
        }
    }
}
