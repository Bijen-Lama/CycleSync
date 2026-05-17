package com.cyclesync.controllers;


/*
 * File name: RidingHistoryServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.model.UserModel;
import com.cyclesync.service.BorrowService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ridingHistory")

/**
 * Class: RidingHistoryServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class RidingHistoryServlet extends BaseServlet {

    private final BorrowService borrowService = new BorrowService();

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        try {
            request.setAttribute("historyList",
                borrowService.getRecordsByUser(user.getUserId()));
            request.getRequestDispatcher("/WEB-INF/pages/ridingHistory.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading riding history.", e);
        }
    }


}