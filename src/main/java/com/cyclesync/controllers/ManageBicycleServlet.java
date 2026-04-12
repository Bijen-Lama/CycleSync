package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.service.BicycleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/manageBicycles")
public class ManageBicycleServlet extends HttpServlet {

    private final BicycleService bicycleService = new BicycleService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response)) return;

        try {
            request.setAttribute("bicycleList", bicycleService.getAllBicycles());
            request.getRequestDispatcher("/WEB-INF/pages/manageBicycles.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading bicycle list.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response)) return;

        String action = request.getParameter("action");

        try {
            switch (action == null ? "" : action) {
                case "add":
                    bicycleService.addBicycle(
                        request.getParameter("bicycleName"),
                        request.getParameter("bicycleType"),
                        request.getParameter("locationCode"),
                        request.getParameter("hourlyRate"),
                        request.getParameter("description")
                    );
                    request.getSession().setAttribute("successMessage", "Bicycle added successfully.");
                    break;

                case "update":
                    bicycleService.updateBicycle(
                        Integer.parseInt(request.getParameter("bicycleId")),
                        request.getParameter("bicycleName"),
                        request.getParameter("bicycleType"),
                        request.getParameter("bicycleStatus"),
                        request.getParameter("locationCode"),
                        request.getParameter("hourlyRate"),
                        request.getParameter("description")
                    );
                    request.getSession().setAttribute("successMessage", "Bicycle updated successfully.");
                    break;

                case "delete":
                    bicycleService.deleteBicycle(
                        Integer.parseInt(request.getParameter("bicycleId"))
                    );
                    request.getSession().setAttribute("successMessage", "Bicycle removed.");
                    break;

                case "updateStatus":
                    bicycleService.updateStatus(
                        Integer.parseInt(request.getParameter("bicycleId")),
                        request.getParameter("bicycleStatus")
                    );
                    request.getSession().setAttribute("successMessage", "Status updated.");
                    break;

                default:
                    request.getSession().setAttribute("errorMessage", "Unknown action.");
            }
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
        } catch (SQLException e) {
            throw new ServletException("Database error managing bicycles.", e);
        }
        response.sendRedirect(response.encodeRedirectURL("manageBicycles"));
    }

    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(response.encodeRedirectURL("login")); return false;
        }
        UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (!user.isAdmin()) {
            response.sendRedirect(response.encodeRedirectURL("memberDashboard")); return false;
        }
        return true;
    }
}