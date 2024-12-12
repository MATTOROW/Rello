package ru.itis.orisproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.orisproject.db.dao.AccountDAO;
import ru.itis.orisproject.models.Account;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/acc-check")
public class AccountCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean rememberMe = "on".equals(req.getParameter("remember"));
        AccountDAO accountDAO = new AccountDAO();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Проверка учетных данных пользователя
        Account acc = accountDAO.getByUsername(username);

        if (acc != null && passwordEncoder.matches(password, acc.password())) {
            // Создаем сессию
            HttpSession session = req.getSession();
            session.setAttribute("account", acc);

            // Если выбран "запомнить меня", создаем токен
            if (rememberMe) {
                String rememberMeToken = UUID.randomUUID().toString();
                Cookie rememberMeCookie = new Cookie("rmmt", rememberMeToken);
                rememberMeCookie.setMaxAge(60 * 60 * 24);
                rememberMeCookie.setPath("/");

                accountDAO.updateRmmtByUsername(username, rememberMeToken);

                resp.addCookie(rememberMeCookie);
            }

            resp.sendRedirect(getServletContext().getContextPath() + "/");
        } else {
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        }
    }
}
