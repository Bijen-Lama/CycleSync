package com.cyclesync.controllers;

import com.cyclesync.dao.UserDao;
import com.cyclesync.model.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("resetEmail") == null) {
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/resetPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("resetEmail") == null) {
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }

        String resetEmail = (String) session.getAttribute("resetEmail");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            request.setAttribute("errorMessage", "Both password fields are required.");
            request.getRequestDispatcher("/WEB-INF/pages/resetPassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/pages/resetPassword.jsp").forward(request, response);
            return;
        }

        try {
            UserModel user = userDao.findByEmail(resetEmail);
            if (user == null) {
                session.removeAttribute("resetEmail");
                response.sendRedirect(request.getContextPath() + "/forgot-password");
                return;
            }

            // Hash the new password
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            boolean success = userDao.updatePassword(user.getUserId(), hashedPassword);

            if (success) {
                session.removeAttribute("resetEmail");
                response.sendRedirect(request.getContextPath() + "/login?registered=true"); // Re-using registered flag for success message
            } else {
                request.setAttribute("errorMessage", "Failed to update password. Please try again.");
                request.getRequestDispatcher("/WEB-INF/pages/resetPassword.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Database error during password reset.", e);
        }
    }
}
