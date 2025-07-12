package com.example.servlet;

import com.example.dao.UserDao;
import com.example.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (userDao.findByUsername(username) != null) {
            resp.getWriter().write("User already exists.");
            return;
        }

        User user = new User(username, password, email);
        userDao.save(user);

        resp.sendRedirect("login.html");
    }
}
