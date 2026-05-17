package com.cyclesync.controllers;


/*
 * File name: EditProfileServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.model.UserModel;
import com.cyclesync.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editProfile")

/**
 * Class: EditProfileServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class EditProfileServlet extends BaseServlet {

    private final UserService userService = new UserService();

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;
        
        request.setAttribute("activePage", "editProfile");
        request.getRequestDispatcher("/WEB-INF/pages/editProfile.jsp").forward(request, response);
    }

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        String fullName = request.getParameter("fullName");
        String userEmail = request.getParameter("userEmail");
        String phoneNumber = request.getParameter("phoneNumber");
        String nationality = request.getParameter("nationality");
        String newPassword = request.getParameter("userPassword");

        try {
            boolean updatedProfile = userService.updateProfile(user.getUserId(), fullName, userEmail, phoneNumber, nationality);
            
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                userService.updatePassword(user.getUserId(), newPassword);
            }
            
            if (updatedProfile) {
                user.setFullName(fullName);
                user.setUserEmail(userEmail);
                user.setPhoneNumber(phoneNumber);
                user.setNationality(nationality);
                request.getSession().setAttribute("loggedInUser", user);
                request.getSession().setAttribute("successMessage", "Profile updated successfully.");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to update profile.");
            }
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
        } catch (SQLException e) {
            throw new ServletException("Database error updating profile", e);
        }

        response.sendRedirect(request.getContextPath() + "/editProfile");
    }
}
