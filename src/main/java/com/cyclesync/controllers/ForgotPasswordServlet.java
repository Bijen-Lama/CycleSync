package com.cyclesync.controllers;

import com.cyclesync.dao.UserDao;
import com.cyclesync.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userEmail = request.getParameter("userEmail");

        if (userEmail == null || userEmail.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email is required.");
            request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
            return;
        }

        try {
            UserModel user = userDao.findByEmail(userEmail.trim());

            if (user == null) {
                request.setAttribute("errorMessage", "Email not found in our system.");
                request.getRequestDispatcher("/WEB-INF/pages/forgotPassword.jsp").forward(request, response);
            } else {
                // In a real app, we would send an email with a token.
                // Here, we'll store the email in session and redirect to reset page for simulation.
                HttpSession session = request.getSession();
                session.setAttribute("resetEmail", userEmail.trim());
                response.sendRedirect(request.getContextPath() + "/reset-password");
            }

        } catch (SQLException e) {
            throw new ServletException("Database error during password reset.", e);
        }
    }
}
