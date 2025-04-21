package com.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.*;
import java.util.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Map<String, User> userStore = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (userStore.containsKey(username)) {
            resp.getWriter().write("User already exists.");
            return;
        }

        User user = new User(username, password, email);
        userStore.put(username, user);

        resp.sendRedirect("login.html");
    }

    public static User getUser(String username) {
        return userStore.get(username);
    }

    public static void addUser(User user) {
        userStore.put(user.getUsername(), user);
    }
}
