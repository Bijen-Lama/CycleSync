package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/manageMembers")
public class ManageMemberServlet extends BaseServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response)) return;

        try {
            request.setAttribute("memberList", userService.getAllMembers());
            request.getRequestDispatcher("/WEB-INF/pages/manageMembers.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading member list.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request, response)) return;

        String action = request.getParameter("action");
        int targetUserId = Integer.parseInt(request.getParameter("userId"));

        try {
            switch (action == null ? "" : action) {
                case "suspend":
                    userService.suspendMember(targetUserId);
                    request.getSession().setAttribute("successMessage", "Member suspended.");
                    break;
                case "activate":
                    userService.activateMember(targetUserId);
                    request.getSession().setAttribute("successMessage", "Member activated.");
                    break;
                case "delete":
                    userService.deleteMember(targetUserId);
                    request.getSession().setAttribute("successMessage", "Member deleted.");
                    break;
                default:
                    request.getSession().setAttribute("errorMessage", "Unknown action.");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error managing members.", e);
        }
        response.sendRedirect(response.encodeRedirectURL("manageMembers"));
    }


}