package ru.itis.orisproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/images/*")
public class ImageLoaderServlet extends HttpServlet {
    private final String iconDirUrl = "/home/pnikita/IdeaProjects/OrisProgect/uploaded/account_icons";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestedImage = req.getPathInfo();
        if (requestedImage == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Image name is required");
            return;
        }

        File imageFile = new File(iconDirUrl, requestedImage);
        if (!imageFile.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
            return;
        }

        resp.setContentType(getServletContext().getMimeType(imageFile.getName()));
        try (InputStream in = new FileInputStream(imageFile); OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
