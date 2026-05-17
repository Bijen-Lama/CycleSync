package com.cyclesync.controllers;


/*
 * File name: LegalServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = { "/terms", "/privacy" })

/**
 * Class: LegalServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class LegalServlet extends HttpServlet {
    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/terms".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/pages/terms.jsp").forward(request, response);
        } else if ("/privacy".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/pages/privacy.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
