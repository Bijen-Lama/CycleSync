package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    /** GET — display the login page */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If already logged in, redirect appropriately
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            UserModel user = (UserModel) session.getAttribute("loggedInUser");
            redirectByRole(user, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    /** POST — handle credential submission */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userEmail    = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");

        // Basic presence validation
        if (userEmail == null || userEmail.trim().isEmpty() ||
            userPassword == null || userPassword.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email and password are required.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        try {
            UserModel user = userService.loginUser(userEmail.trim(), userPassword);

            if (user == null) {
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                return;
            }

            // Successful login — create session
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedInUser", user);
            session.setAttribute("userId",       user.getUserId());
            session.setAttribute("userRole",     user.getUserRole());
            session.setMaxInactiveInterval(60 * 30);  // 30 minutes

            redirectByRole(user, response);

        } catch (SQLException e) {
            throw new ServletException("Database error during login.", e);
        }
    }

    private void redirectByRole(UserModel user, HttpServletResponse response) throws IOException {
        if (user.isAdmin()) {
            response.sendRedirect(response.encodeRedirectURL("adminDashboard"));
        } else {
            response.sendRedirect(response.encodeRedirectURL("memberDashboard"));
        }
    }
}