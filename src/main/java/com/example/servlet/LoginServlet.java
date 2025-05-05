package com.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, username);
            if (user != null && user.getPassword().equals(password)) {
                req.getSession().setAttribute("user", user);
                resp.sendRedirect("files");
            } else {
                resp.getWriter().write("Invalid credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Database error.");
        }
    }
}