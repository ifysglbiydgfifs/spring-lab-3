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
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            User user = query.uniqueResult();
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