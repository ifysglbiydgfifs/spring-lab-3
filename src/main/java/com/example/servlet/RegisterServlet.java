package com.example.servlet;

import com.example.dao.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (userDAO.findByUsername(username) != null) {
            resp.getWriter().write("User already exists.");
            return;
        }

        User user = new User(username, password, email);
        userDAO.save(user);

        resp.sendRedirect("login.html");
    }
}
