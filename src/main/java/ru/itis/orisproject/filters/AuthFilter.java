package ru.itis.orisproject.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.RmmtService;

import java.io.IOException;

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
                        httpRequest.getServletPath().startsWith("/static") ||
                        httpRequest.getServletPath().startsWith("/register")
        ) {
            chain.doFilter(request, response);
        } else if (httpRequest.getServletPath().isEmpty()) {
            HttpSession session = httpRequest.getSession(false);
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
                RmmtService rmmtService = (RmmtService) request.getServletContext().getAttribute("RmmtService");
                AccountEntity accountEntity = rmmtService.getAccByToken(rememberMeToken);
                if (accountEntity != null) {
                    session = httpRequest.getSession(true);
                    session.setAttribute("account", accountEntity);
                }
            }
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
                    RmmtService rmmtService = (RmmtService) request.getServletContext().getAttribute("RmmtService");
                    AccountEntity accountEntity = rmmtService.getAccByToken(rememberMeToken);
                    if (accountEntity != null) {
                        session = httpRequest.getSession(true);
                        session.setAttribute("account", accountEntity);
                        chain.doFilter(request, response);
                    } else {
                        httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/login");
                    }
                } else {
                    httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/login");
                }
            }
        }
    }
}
