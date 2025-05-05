package com.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                if (dbPassword.equals(password)) {
                    User user = new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                    req.getSession().setAttribute("user", user);
                    resp.sendRedirect("files");
                    return;
                }
            }

            resp.getWriter().write("Invalid credentials");

        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("Database error.");
        }
    }
}
