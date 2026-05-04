package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.service.UserService;
import com.cyclesync.config.DBConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/impersonate")
public class ImpersonateServlet extends BaseServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if ("exit".equals(action)) {
            UserModel adminUser = (UserModel) session.getAttribute("impersonatorAdmin");
            if (adminUser != null) {
                session.setAttribute("loggedInUser", adminUser);
                session.setAttribute("userId", adminUser.getUserId());
                session.setAttribute("userRole", adminUser.getUserRole());
                session.removeAttribute("impersonatorAdmin");
                session.setAttribute("successMessage", "Exited impersonation mode.");
                response.sendRedirect(request.getContextPath() + "/adminDashboard");
                return;
            }
        }

        if (!isAdmin(request, response)) return;

        try {
            int targetUserId = Integer.parseInt(request.getParameter("userId"));
            UserModel targetUser = userService.getMemberById(targetUserId);
            
            if (targetUser != null && !targetUser.isAdmin()) {
                UserModel adminUser = (UserModel) session.getAttribute("loggedInUser");
                
                try (Connection conn = DBConfig.getConnection();
                     PreparedStatement ps = conn.prepareStatement("INSERT INTO impersonation_logs (adminId, targetUserId) VALUES (?, ?)")) {
                    ps.setInt(1, adminUser.getUserId());
                    ps.setInt(2, targetUserId);
                    ps.executeUpdate();
                } catch (Exception e) {
                    // Log error but continue impersonation
                    System.err.println("Failed to log impersonation: " + e.getMessage());
                }
                
                session.setAttribute("impersonatorAdmin", adminUser);
                session.setAttribute("loggedInUser", targetUser);
                session.setAttribute("userId", targetUser.getUserId());
                session.setAttribute("userRole", targetUser.getUserRole());
                
                session.setAttribute("successMessage", "You are now impersonating " + targetUser.getFullName());
                response.sendRedirect(request.getContextPath() + "/memberDashboard");
            } else {
                session.setAttribute("errorMessage", "Cannot impersonate this user.");
                response.sendRedirect(request.getContextPath() + "/manageMembers");
            }
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Invalid user ID.");
            response.sendRedirect(request.getContextPath() + "/manageMembers");
        }
    }
}
