package com.example.servlet;

import com.example.dao.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("files");
        } else {
            resp.getWriter().write("Invalid credentials");
        }
    }
}
