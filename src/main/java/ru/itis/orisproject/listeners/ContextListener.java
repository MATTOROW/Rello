package ru.itis.orisproject.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.services.*;

import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Properties properties = new Properties();
            properties.load(getClass()
                    .getClassLoader()
                    .getResourceAsStream("/db.properties"));
            sce.getServletContext().setAttribute("AccountService", new AccountService());
            sce.getServletContext().setAttribute("RmmtService", new RmmtService());
            sce.getServletContext().setAttribute("ProjectService", new ProjectService());
            sce.getServletContext().setAttribute("AccountProjectService", new AccountProjectService());
            sce.getServletContext().setAttribute("TaskService", new TaskService());
            sce.getServletContext().setAttribute("SubtaskService", new SubtaskService());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConfig.closeAll();
    }
}
