package com.cyclesync.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/terms", "/privacy"})
public class LegalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
