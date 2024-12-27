package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        Cookie lastRmmtCookie = new Cookie("rmmt", "");
        lastRmmtCookie.setMaxAge(0);
        lastRmmtCookie.setPath("/");
        resp.addCookie(lastRmmtCookie);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
