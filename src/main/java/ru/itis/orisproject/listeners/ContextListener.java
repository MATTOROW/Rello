package ru.itis.orisproject.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.orisproject.db.DBConnection;
import ru.itis.orisproject.db.dao.AccountDAO;
import ru.itis.orisproject.db.dao.RmmtDAO;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            DBConnection.getDBConnection().initDB(
                    properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password"));
            sce.getServletContext().setAttribute("AccountDAO", new AccountDAO());
            sce.getServletContext().setAttribute("RmmtDAO", new RmmtDAO());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.getDBConnection().closeAll();
    }
}
