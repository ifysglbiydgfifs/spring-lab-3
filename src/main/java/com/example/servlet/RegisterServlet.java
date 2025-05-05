package com.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Map<String, User> userStore = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                resp.getWriter().write("User already exists.");
                return;
            }

            PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.setString(3, email);
            insertStmt.executeUpdate();

            resp.sendRedirect("login.html");

        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("Database error.");
        }
    }

    public static User getUser(String username) {
        return userStore.get(username);
    }

    public static void addUser(User user) {
        userStore.put(user.getUsername(), user);
    }
}
