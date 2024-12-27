package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.orisproject.models.AccountEntity;

import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
        if (account != null) {
            req.setAttribute("username", account.getUsername());
            req.setAttribute("description", account.getDescription());
            req.setAttribute("email", account.getEmail());
            req.setAttribute("iconUrl", account.getIconPath());
            req.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("You must log in to view this page.");
        }
    }
}
