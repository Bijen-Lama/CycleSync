package com.cyclesync.controllers;


/*
 * File name: FeedbackServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.dao.FeedbackDao;
import com.cyclesync.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminFeedback")

/**
 * Class: FeedbackServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class FeedbackServlet extends BaseServlet {
    
    private final FeedbackDao feedbackDao = new FeedbackDao();

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request, response)) return;

        try {
            request.setAttribute("feedbackList", feedbackDao.findAll());
            request.getRequestDispatcher("/WEB-INF/pages/adminFeedback.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading feedback.", e);
        }
    }

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request, response)) return;

        int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
        String status = request.getParameter("status");

        try {
            feedbackDao.updateStatus(feedbackId, status);
            response.sendRedirect("adminFeedback");
        } catch (SQLException e) {
            throw new ServletException("Error updating feedback status.", e);
        }
    }
}
