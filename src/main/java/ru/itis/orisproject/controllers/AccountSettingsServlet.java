package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.itis.orisproject.dto.request.AccountRequest;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.services.AccountService;
import ru.itis.orisproject.services.PasswordCoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/account/settings")
@MultipartConfig
public class AccountSettingsServlet extends HttpServlet {
    private final String iconDirUrl = "/home/pnikita/IdeaProjects/OrisProgect/uploaded/account_icons";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
        req.setAttribute("username", account.getUsername());
        req.setAttribute("password", account.getPassword());
        req.setAttribute("email", account.getEmail());
        req.setAttribute("description", account.getDescription());

        req.setAttribute("iconUrl", "images/%s".formatted(account.getIconPath()));

        // Перенаправляем на страницу редактирования аккаунта
        req.getRequestDispatcher("/WEB-INF/views/account_settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Извлекаем данные из формы
        String username = req.getParameter("username").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();
        String description = req.getParameter("description").trim();
        AccountEntity account = (AccountEntity) req.getSession().getAttribute("account");
        if (username.isEmpty()) {
            username = account.getUsername();
        }
        if (email.isEmpty()) {
            email = account.getEmail();
        }
        if (password.isEmpty()) {
            password = account.getPassword();
        } else
            password = PasswordCoder.encode(password);
        if (description.isEmpty()) {
            description = account.getDescription();
        }
        // Обработка иконки
        Part iconPart = req.getPart("icon"); // Получаем файл иконки
        String newImage = null;
        if (iconPart != null && iconPart.getSize() > 0) {
            // Проверка MIME типа
            String mimeType = iconPart.getContentType();
            if (!mimeType.split("/")[0].equals("image")) {
                // Если MIME тип не разрешен
                req.setAttribute("message", "Only image files are allowed.");
                doGet(req, resp); // Перенаправляем на страницу с сообщением об ошибке
                return;
            }
            // Если файл прошел все проверки, сохраняем его
            newImage = iconPart.getSubmittedFileName();
            String oldImage = account.getIconPath();
            if (Files.exists(Path.of(iconDirUrl + "/" + newImage))) {
                newImage = newImage.split("\\.")[0] + System.nanoTime() + "." + newImage.split("\\.")[1];
            }
            Files.deleteIfExists(Path.of(iconDirUrl + "/" + oldImage));
            iconPart.write(iconDirUrl + "/" + newImage); // Делегируем сохранение
        } else {
            newImage = account.getIconPath();
        }

        AccountService service = (AccountService) getServletContext().getAttribute("AccountService");
        AccountRequest accountRequest = new AccountRequest(
                username,
                password,
                email,
                newImage,
                description
        );
        int code = service.updateByUsername(accountRequest, account.getUsername());
        if (code == 1) {
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setDescription(description);
            account.setIconPath(newImage);
            req.getSession().setAttribute("account", account);

            // Уведомление об успешном обновлении
            req.setAttribute("message", "Account settings updated successfully.");
        } else if (code == 0) {
            req.setAttribute("message", "This username or email are already taken.");
        } else if (code == -1) {
            req.setAttribute("message", "Error while saving account. Try again.");
        }


        // Перенаправляем обратно на страницу редактирования
        doGet(req, resp);
    }
}
