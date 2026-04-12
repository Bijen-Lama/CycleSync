package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.service.BicycleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/searchBicycles")
public class SearchBicycleServlet extends HttpServlet {

    private final BicycleService bicycleService = new BicycleService();

    /** GET — load page (shows all available by default) */
    @Override
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

    private UserModel getLoggedInUser(HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(response.encodeRedirectURL("login")); return null;
        }
        return (UserModel) session.getAttribute("loggedInUser");
    }
}