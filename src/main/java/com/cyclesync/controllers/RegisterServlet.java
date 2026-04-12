package com.cyclesync.controllers;

import com.cyclesync.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName     = request.getParameter("fullName");
        String userEmail    = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String confirmPass  = request.getParameter("confirmPassword");
        String phoneNumber  = request.getParameter("phoneNumber");
        String userAddress  = request.getParameter("userAddress");

        // Validation
        if (fullName == null || fullName.trim().isEmpty() ||
            userEmail == null || userEmail.trim().isEmpty() ||
            userPassword == null || userPassword.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Full name, email, and password are required.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (!userPassword.equals(confirmPass)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (userPassword.length() < 8) {
            request.setAttribute("errorMessage", "Password must be at least 8 characters.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        try {
            userService.registerMember(fullName.trim(), userEmail.trim(),
                                       userPassword, phoneNumber, userAddress);
            response.sendRedirect(response.encodeRedirectURL("login?registered=true"));

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error during registration.", e);
        }
    }
}