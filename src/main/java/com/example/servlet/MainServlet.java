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

    private static final String DEFAULT_PATH = System.getProperty("user.home");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String dirPath = req.getParameter("path");
        if (dirPath != null) {
            dirPath = URLDecoder.decode(dirPath, "UTF-8");
        }

        File dir = null;
        File[] files;

        if (dirPath == null || dirPath.isEmpty()) {
            files = File.listRoots();
            dirPath = null;
        } else {
            dir = new File(dirPath);
            if (!dir.exists() || !dir.isDirectory()) {
                dir = new File(DEFAULT_PATH);
                dirPath = dir.getAbsolutePath();
            }
            files = dir.listFiles();
        }

        req.setAttribute("files", files);
        req.setAttribute("dirPath", dirPath);
        req.setAttribute("parentPath", dir != null ? dir.getParent() : null);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        req.setAttribute("currentTime", sdf.format(new Date()));

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }
}
