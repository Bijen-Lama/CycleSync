package com.cyclesync.controllers;


/*
 * File name: BaseServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

import com.cyclesync.model.UserModel;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Class: BaseServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public abstract class BaseServlet extends HttpServlet {


    // Processes the request and determines the next navigation step

    protected void setCacheHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
    }


    // Processes the request and determines the next navigation step

    protected UserModel getLoggedInUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCacheHeaders(response);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login"); 
            return null;
        }
        return (UserModel) session.getAttribute("loggedInUser");
    }


    // Processes the request and determines the next navigation step

    protected boolean isAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserModel user = getLoggedInUser(request, response);
        if (user == null) {
            return false;
        }
        if (!user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/memberDashboard"); 
            return false;
        }
        return true;
    }
}
