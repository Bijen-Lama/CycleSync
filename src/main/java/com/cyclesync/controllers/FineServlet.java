package com.cyclesync.controllers;


/*
 * File name: FineServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.model.UserModel;
import com.cyclesync.service.FineService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/fines")

/**
 * Class: FineServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class FineServlet extends BaseServlet {

    private final FineService fineService = new FineService();

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        try {
            if (user.isAdmin()) {
                // Admin sees all fines
                request.setAttribute("fineList",    fineService.getAllFines());
                request.setAttribute("pendingCount",fineService.getPendingFines().size());
            } else {
                // Member sees only their own fines
                request.setAttribute("fineList",
                    fineService.getFinesByUser(user.getUserId()));
                request.setAttribute("totalPending",
                    fineService.getTotalPendingFinesByUser(user.getUserId()));
            }
            request.getRequestDispatcher("/WEB-INF/pages/fine.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading fines.", e);
        }
    }

    /** POST — admin resolves a fine (mark paid or waive) */
    @Override

    // Special method handling request lifecycle or component initialization
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = getLoggedInUser(request, response);
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(response.encodeRedirectURL("login")); return;
        }

        String action      = request.getParameter("action");
        int    fineId      = Integer.parseInt(request.getParameter("fineId"));
        String adminNotes  = request.getParameter("adminNotes");

        try {
            switch (action == null ? "" : action) {
                case "markPaid":
                    fineService.markFinePaid(fineId, adminNotes);
                    request.getSession().setAttribute("successMessage", "Fine marked as paid.");
                    break;
                case "waive":
                    fineService.waiveFine(fineId, adminNotes);
                    request.getSession().setAttribute("successMessage", "Fine waived.");
                    break;
                default:
                    request.getSession().setAttribute("errorMessage", "Unknown action.");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error resolving fine.", e);
        }
        response.sendRedirect(response.encodeRedirectURL("fines"));
    }


}