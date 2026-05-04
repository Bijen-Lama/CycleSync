package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            UserModel user = (UserModel) session.getAttribute("loggedInUser");
            redirectByRole(user, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userEmail    = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");

        if (userEmail == null || userEmail.trim().isEmpty() ||
            userPassword == null || userPassword.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email and password are required.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        HttpSession checkSession = request.getSession(true);
        
        // Brute-force protection check
        Integer failedAttempts = (Integer) checkSession.getAttribute("failedAttempts");
        if (failedAttempts == null) failedAttempts = 0;
        
        Long lockoutTime = (Long) checkSession.getAttribute("lockoutTime");
        if (lockoutTime != null) {
            if (System.currentTimeMillis() < lockoutTime) {
                request.setAttribute("errorMessage", "Too many failed attempts. Please wait 5 minutes.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                return;
            } else {
                // Lockout period expired
                checkSession.removeAttribute("lockoutTime");
                failedAttempts = 0;
                checkSession.setAttribute("failedAttempts", failedAttempts);
            }
        }

        try {
            UserModel user = userDao.findByEmail(userEmail.trim());

            if (user == null) {
                incrementFailedAttempts(checkSession, failedAttempts);
                request.setAttribute("errorMessage", "Email not found.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                return;
            }

            if (!user.isActive()) {
                request.setAttribute("errorMessage", "Your account is suspended. Please contact admin.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                return;
            }

            boolean passwordMatch = false;
            try {
                passwordMatch = BCrypt.checkpw(userPassword, user.getUserPassword());
            } catch (Exception e) {
                // In case of plain text passwords from old system
                passwordMatch = userPassword.equals(user.getUserPassword());
            }

            if (!passwordMatch) {
                failedAttempts++;
                if (failedAttempts >= 3) {
                    checkSession.setAttribute("lockoutTime", System.currentTimeMillis() + (5 * 60 * 1000));
                    request.setAttribute("errorMessage", "Account locked. Please try again in 5 minutes.");
                } else {
                    checkSession.setAttribute("failedAttempts", failedAttempts);
                    request.setAttribute("errorMessage", "Incorrect password.");
                }
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                return;
            }

            // Successful login — create session & reset attempts
            checkSession.removeAttribute("failedAttempts");
            checkSession.removeAttribute("lockoutTime");
            
            checkSession.setAttribute("loggedInUser", user);
            checkSession.setAttribute("userId",       user.getUserId());
            checkSession.setAttribute("userRole",     user.getUserRole());
            checkSession.setMaxInactiveInterval(60 * 30);  // 30 minutes

            redirectByRole(user, response);

        } catch (SQLException e) {
            throw new ServletException("Database error during login.", e);
        }
    }

    private void incrementFailedAttempts(HttpSession session, int currentAttempts) {
        currentAttempts++;
        if (currentAttempts >= 3) {
            session.setAttribute("lockoutTime", System.currentTimeMillis() + (5 * 60 * 1000));
        }
        session.setAttribute("failedAttempts", currentAttempts);
    }

    private void redirectByRole(UserModel user, HttpServletResponse response) throws IOException {
        if (user.isAdmin()) {
            response.sendRedirect(response.encodeRedirectURL("adminDashboard"));
        } else {
            response.sendRedirect(response.encodeRedirectURL("memberDashboard"));
        }
    }
}