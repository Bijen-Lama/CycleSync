package com.cyclesync.controllers;


/*
 * File name: ManageBicycleServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.model.UserModel;
import com.cyclesync.service.BicycleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/manageBicycles")

/**
 * Class: ManageBicycleServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class ManageBicycleServlet extends BaseServlet {

    private final BicycleService bicycleService = new BicycleService();

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response))
            return;

        try {
            request.setAttribute("bicycleList", bicycleService.getAllBicycles());
            request.getRequestDispatcher("/WEB-INF/pages/manageBicycles.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading bicycle list.", e);
        }
    }

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response))
            return;

        String action = request.getParameter("action");

        try {
            switch (action == null ? "" : action) {
                case "add":
                    bicycleService.addBicycle(
                            request.getParameter("bicycleName"),
                            request.getParameter("bicycleType"),
                            request.getParameter("locationCode"),
                            request.getParameter("hourlyRate"),
                            request.getParameter("description"));
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
                            request.getParameter("description"));
                    request.getSession().setAttribute("successMessage", "Bicycle updated successfully.");
                    break;

                case "delete":
                    String delIdStr = request.getParameter("bicycleId");
                    if (delIdStr != null && !delIdStr.isEmpty()) {
                        try {
                            bicycleService.deleteBicycle(Integer.parseInt(delIdStr));
                            request.getSession().setAttribute("successMessage", "Bicycle removed.");
                        } catch (NumberFormatException e) {
                            request.getSession().setAttribute("errorMessage", "Invalid Bicycle ID format.");
                        }
                    } else {
                        request.getSession().setAttribute("errorMessage", "Bicycle ID is missing.");
                    }
                    break;

                case "updateStatus":
                    bicycleService.updateStatus(
                            Integer.parseInt(request.getParameter("bicycleId")),
                            request.getParameter("bicycleStatus"));
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

}