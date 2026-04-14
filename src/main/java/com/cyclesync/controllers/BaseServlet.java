package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {

    protected UserModel getLoggedInUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(response.encodeRedirectURL("login")); 
            return null;
        }
        return (UserModel) session.getAttribute("loggedInUser");
    }

    protected boolean isAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserModel user = getLoggedInUser(request, response);
        if (user == null) {
            return false;
        }
        if (!user.isAdmin()) {
            response.sendRedirect(response.encodeRedirectURL("memberDashboard")); 
            return false;
        }
        return true;
    }
}
