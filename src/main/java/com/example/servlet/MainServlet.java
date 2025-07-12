package com.example.servlet;

import com.example.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/files")
public class MainServlet extends HttpServlet {

    private static final String DEFAULT_PATH = System.getProperty("user.home");
    private static final String BASE_DIR = "D:\\filemanager";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String userHomePath = BASE_DIR + File.separator + user.getUsername();
        File userHomeDir = new File(userHomePath);
        if (!userHomeDir.exists()) {
            userHomeDir.mkdirs();
        }

        String dirPath = req.getParameter("path");
        if (dirPath != null) {
            dirPath = URLDecoder.decode(dirPath, "UTF-8");
        } else {
            dirPath = userHomePath;
        }

        File dir = new File(dirPath);

        if (!dir.getCanonicalPath().startsWith(userHomeDir.getCanonicalPath())) {
            dir = userHomeDir;
            dirPath = userHomePath;
        }

        File[] files = dir.listFiles();
        req.setAttribute("files", files);
        req.setAttribute("dirPath", dir.getAbsolutePath());
        req.setAttribute("parentPath", dir.getParent());
        req.setAttribute("currentTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        req.setAttribute("username", user.getUsername());

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }
}
