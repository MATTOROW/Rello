package ru.itis.orisproject.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.AccountProjectService;

import java.io.IOException;
import java.util.UUID;

@WebFilter(urlPatterns = {"/project/*", "/task/*", "/subtask/*"})
public class AccessProjectFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

        AccountEntity account = (AccountEntity) httpRequest.getSession().getAttribute("account");
        AccountProjectService accountProjectService = (AccountProjectService) getServletContext().getAttribute("AccountProjectService");
        String projectId = (String) httpRequest.getSession().getAttribute("projectId");
        String taskId = (String) httpRequest.getSession().getAttribute("taskId");
        String subtaskId = (String) httpRequest.getSession().getAttribute("subtaskId");
        if (projectId == null || taskId == null || subtaskId == null) {
            chain.doFilter(req, res);
        } else if (accountProjectService.hasAccess(UUID.fromString(projectId), account.getUsername())) {
            chain.doFilter(req, res);
        } else if (accountProjectService.hasAccess(UUID.fromString(taskId), account.getUsername())) {
            chain.doFilter(req, res);
        } else if (accountProjectService.hasAccess(UUID.fromString(subtaskId), account.getUsername())) {
            chain.doFilter(req, res);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        }
    }
}
