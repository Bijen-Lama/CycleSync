package com.cyclesync.controllers;

import com.cyclesync.model.UserModel;
import com.cyclesync.service.BorrowService;
import com.cyclesync.service.FineService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/memberDashboard")
public class MemberDashboardServlet extends HttpServlet {

    private final BorrowService borrowService = new BorrowService();
    private final FineService   fineService   = new FineService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = getLoggedInMember(request, response);
        if (user == null) return;

        try {
            int userId = user.getUserId();

            request.setAttribute("activeBorrow",    borrowService.getActiveBorrowByUser(userId));
            request.setAttribute("recentHistory",   borrowService.getRecordsByUser(userId));
            request.setAttribute("pendingFines",    fineService.getFinesByUser(userId));
            request.setAttribute("totalPendingFine",fineService.getTotalPendingFinesByUser(userId));

            request.getRequestDispatcher("/WEB-INF/pages/memberDashboard.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error loading member dashboard.", e);
        }
    }

    private UserModel getLoggedInMember(HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(response.encodeRedirectURL("login")); return null;
        }
        return (UserModel) session.getAttribute("loggedInUser");
    }
}