package com.cyclesync.controllers;

import com.cyclesync.dao.TransactionDao;
import com.cyclesync.model.TransactionModel;
import com.cyclesync.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends BaseServlet {

    private final TransactionDao transactionDao = new TransactionDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        try {
            if ("ADMIN".equals(user.getUserRole())) {
                List<TransactionModel> allTx = transactionDao.findAll();
                request.setAttribute("transactions", allTx);
                request.setAttribute("totalRevenue", transactionDao.getTotalRevenue());
                request.getRequestDispatcher("/WEB-INF/pages/adminTransactions.jsp").forward(request, response);
            } else {
                List<TransactionModel> myTx = transactionDao.findByUserId(user.getUserId());
                request.setAttribute("transactions", myTx);
                request.getRequestDispatcher("/WEB-INF/pages/memberTransactions.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error loading transactions", e);
        }
    }
}
