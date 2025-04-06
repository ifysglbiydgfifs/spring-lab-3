package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    private static final String BASE_PATH = "C://";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String dirPath = req.getParameter("path");
        if (dirPath != null) {
            dirPath = URLDecoder.decode(dirPath, "UTF-8");
        }

        if (dirPath == null || dirPath.isEmpty()) {
            dirPath = BASE_PATH;
        }

        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            dirPath = BASE_PATH;
            dir = new File(dirPath);
        }

        File[] files = dir.listFiles();
        req.setAttribute("files", files);
        req.setAttribute("dirPath", dirPath);

        String parentPath = dir.getParent();
        req.setAttribute("parentPath", parentPath);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        req.setAttribute("currentTime", sdf.format(new Date()));

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }
}
