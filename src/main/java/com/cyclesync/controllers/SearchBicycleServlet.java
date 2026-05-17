package com.cyclesync.controllers;


/*
 * File name: SearchBicycleServlet.java
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

@WebServlet("/searchBicycles")

/**
 * Class: SearchBicycleServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class SearchBicycleServlet extends BaseServlet {

    private final BicycleService bicycleService = new BicycleService();

    /** GET — load page (shows all available by default) */
    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (getLoggedInUser(request, response) == null) return;

        String bicycleType = request.getParameter("bicycleType");

        try {
            request.setAttribute("bikeResults",    bicycleService.searchByType(bicycleType));
            request.setAttribute("selectedType",   bicycleType != null ? bicycleType : "ALL");
            request.getRequestDispatcher("/WEB-INF/pages/searchBicycles.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error searching bicycles.", e);
        }
    }


}