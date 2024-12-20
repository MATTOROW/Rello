package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.AccountService;
import ru.itis.orisproject.services.HashDeviceId;
import ru.itis.orisproject.services.PasswordCoder;
import ru.itis.orisproject.services.RmmtService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/acc-check")
public class AccountCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean rememberMe = "on".equals(req.getParameter("remember"));
        AccountService accountService = (AccountService) req.getServletContext().getAttribute("AccountService");
        String errorMessage = null;

        if (!username.isEmpty() && !password.isEmpty()) {
            // Проверка учетных данных пользователя
            AccountEntity acc = accountService.getEntityByUsername(username);

            if (acc != null && PasswordCoder.matches(password, acc.getPassword())) {
                // Создаем сессию
                HttpSession session = req.getSession();
                session.setAttribute("account", acc);

                // Удаляем содержащуюся куку
                Cookie lastRmmtCookie = new Cookie("rmmt", "");
                lastRmmtCookie.setMaxAge(0);
                lastRmmtCookie.setPath("/");
                resp.addCookie(lastRmmtCookie);

                // Если выбран "запомнить меня", создаем токен
                if (rememberMe) {
                    RmmtService rmmtService = (RmmtService) req.getServletContext().getAttribute("RmmtService");
                    String rememberMeToken = UUID.randomUUID().toString();
                    Cookie rememberMeCookie = new Cookie("rmmt", rememberMeToken);
                    rememberMeCookie.setMaxAge(60 * 60 * 24);
                    rememberMeCookie.setPath("/");

                    String deviceHash = HashDeviceId.hashString(req.getHeader("User-Agent"));

                    if (rmmtService.deviceRemembered(deviceHash)) {
                        rmmtService.updateAccToken(username, rememberMeToken, deviceHash);
                    } else {
                        rmmtService.save(username, rememberMeToken, deviceHash);
                    }

                    resp.addCookie(rememberMeCookie);
                }
            } else {
                errorMessage = "You have entered an incorrect username or password!";
            }
        } else {
            errorMessage = "You have not entered all data!";
        }

        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(getServletContext().getContextPath() + "/");
        }
    }
}
