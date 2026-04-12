package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.service.FineService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/fines")
public class FineServlet extends HttpServlet {

    private final FineService fineService = new FineService();

    @Override
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
            request.getRequestDispatcher("/WEB-INF/pages/fines.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading fines.", e);
        }
    }

    /** POST — admin resolves a fine (mark paid or waive) */
    @Override
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

    private UserModel getLoggedInUser(HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(response.encodeRedirectURL("login")); return null;
        }
        return (UserModel) session.getAttribute("loggedInUser");
    }
}