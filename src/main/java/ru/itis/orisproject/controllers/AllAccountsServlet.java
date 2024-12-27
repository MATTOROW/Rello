package ru.itis.orisproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.dto.response.AccountResponse;
import ru.itis.orisproject.services.AccountService;

import java.io.IOException;
import java.util.List;

@WebServlet("/accounts")
public class AllAccountsServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        if (username == null || username.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        AccountService accountService = (AccountService) getServletContext().getAttribute("AccountService");
        List<AccountResponse> users = accountService.getByUsernameILike(username);

        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(users));
    }
}
