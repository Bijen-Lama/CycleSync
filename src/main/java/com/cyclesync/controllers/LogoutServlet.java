package com.cyclesync.controllers;


/*
 * File name: LogoutServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")

/**
 * Class: LogoutServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class LogoutServlet extends HttpServlet {

    @Override

    // Special method handling request lifecycle or component initialization
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
}