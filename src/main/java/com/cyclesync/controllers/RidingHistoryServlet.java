package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.service.BorrowService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ridingHistory")
public class RidingHistoryServlet extends BaseServlet {

    private final BorrowService borrowService = new BorrowService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        try {
            request.setAttribute("historyList",
                borrowService.getRecordsByUser(user.getUserId()));
            request.getRequestDispatcher("/WEB-INF/pages/ridingHistory.jsp")
                   .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading riding history.", e);
        }
    }


}