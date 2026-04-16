package com.cyclesync.controllers;

import com.cyclesync.model.BorrowRecordModel;
import com.cyclesync.model.UserModel;
import com.cyclesync.service.BorrowService;
import com.cyclesync.service.FineService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/returnBike")
public class ReturnServlet extends BaseServlet {

    private final BorrowService borrowService = new BorrowService();
    private final FineService   fineService   = new FineService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        try {
            int recordId = Integer.parseInt(request.getParameter("recordId"));

            // Process return — calculates hours and cost
            BorrowRecordModel completedRecord = borrowService.returnBicycle(recordId);

            // Automatically issue a late fine if overdue
            int fineId = fineService.issueLateReturnFine(completedRecord);

            if (fineId > 0) {
                request.getSession().setAttribute("successMessage",
                    "Bike returned. A late return fine has been issued. Check 'My Fines'.");
            } else {
                request.getSession().setAttribute("successMessage",
                    "Bike returned successfully! Total cost: NPR " + completedRecord.getTotalCost());
            }
            response.sendRedirect(response.encodeRedirectURL("memberDashboard"));

        } catch (IllegalArgumentException | IllegalStateException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(response.encodeRedirectURL("memberDashboard"));
        } catch (SQLException e) {
            throw new ServletException("Database error during return.", e);
        }
    }


}