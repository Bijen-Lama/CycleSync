package com.cyclesync.controllers;


/*
 * File name: TransactionServlet.java
 * Description: CycleSync Controller Servlet
 *
 * This file is part of the CycleSync project.
 * It provides essential architecture for the application.
 */

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

/**
 * Class: TransactionServlet
 * Role: Handles incoming HTTP requests and coordinates client server response flows
 *
 * This handles primary logic and coordinates system processes.
 */
public class TransactionServlet extends BaseServlet {

    private final TransactionDao transactionDao = new TransactionDao();

    @Override

    // Special method handling request lifecycle or component initialization
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
