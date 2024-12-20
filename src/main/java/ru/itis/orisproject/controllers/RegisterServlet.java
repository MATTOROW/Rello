package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.orisproject.dto.request.AccountRequest;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.AccountService;
import ru.itis.orisproject.services.PasswordCoder;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        String errorMessage = null;
        AccountService accountService = (AccountService) req.getServletContext().getAttribute("AccountService");

        if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
            if (isEmailValid(email)) {
                String hashPassword = PasswordCoder.encode(password);
                AccountRequest acc = new AccountRequest(username, hashPassword, email, null, null);
                int saved = accountService.save(acc);
                if (saved == 0) {
                    errorMessage = "A user with such username or email already exists!";
                } else if (saved == -1) {
                    errorMessage = "Error with database, try again.";
                }
            } else {
                errorMessage = "Your email does not matches pattern!";
            }
        } else {
            errorMessage = "You have not entered all data!";
        }

        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(getServletContext().getContextPath() + "/");
        }
    }

    private boolean isEmailValid(String email) {
        String pattern = "(?i)^[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}$";
        return email.matches(pattern);
    }
}
