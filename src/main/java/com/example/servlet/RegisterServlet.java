package com.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            if (query.uniqueResult() != null) {
                resp.getWriter().write("User already exists.");
                return;
            }

            Transaction tx = session.beginTransaction();
            User newUser = new User(username, password, email);
            session.persist(newUser);
            tx.commit();

            resp.sendRedirect("login.html");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Database error.");
        }
    }
}