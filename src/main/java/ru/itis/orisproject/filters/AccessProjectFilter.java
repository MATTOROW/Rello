package ru.itis.orisproject.filters;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;

@WebFilter("/*")
public class AccessProjectFilter extends HttpFilter {
}
