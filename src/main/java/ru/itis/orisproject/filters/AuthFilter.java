package ru.itis.orisproject.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.orisproject.db.dao.AccountDAO;
import ru.itis.orisproject.models.Account;

import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (
                httpRequest.getServletPath().startsWith("/login") ||
                        httpRequest.getServletPath().startsWith("/acc-check") ||
                        httpRequest.getServletPath().startsWith("/css") ||
                        httpRequest.getServletPath().startsWith("/img")) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = httpRequest.getSession(false);
            if (session != null && session.getAttribute("account") != null) {
                chain.doFilter(request, response);
            } else {
                Cookie[] cookies = httpRequest.getCookies();
                String rememberMeToken = null;

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("rmmt".equals(cookie.getName())) {
                            rememberMeToken = cookie.getValue();
                            break;
                        }
                    }
                }

                if (rememberMeToken != null) {
                    AccountDAO accountDAO = new AccountDAO();
                    Account account = accountDAO.getByRmmt(rememberMeToken);

                    if (account != null) {
                        session = httpRequest.getSession(true);
                        session.setAttribute("account", account);

                        String newToken = UUID.randomUUID().toString();
                        Cookie newRememberMeCookie = new Cookie("rmmt", newToken);
                        newRememberMeCookie.setMaxAge(24 * 60 * 60);
                        newRememberMeCookie.setPath("/");
                        httpResponse.addCookie(newRememberMeCookie);

                        accountDAO.updateRmmtByUsername(account.username(), newToken);
                        chain.doFilter(request, response);
                    } else {
                        httpRequest.getRequestDispatcher("/login").forward(request, response);
                    }
                } else {
                    httpRequest.getRequestDispatcher("/login").forward(request, response);
                }
            }
        }
    }
}
