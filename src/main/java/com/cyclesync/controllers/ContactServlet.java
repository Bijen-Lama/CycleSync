package com.cyclesync.controllers;

import com.cyclesync.dao.FeedbackDao;
import com.cyclesync.model.FeedbackModel;
import com.cyclesync.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/contact")
public class ContactServlet extends BaseServlet {
    
    private final FeedbackDao feedbackDao = new FeedbackDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;
        
        request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = getLoggedInUser(request, response);
        if (user == null) return;

        String type = request.getParameter("type");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        if (type == null || subject == null || message == null || subject.trim().isEmpty() || message.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
            return;
        }

        FeedbackModel feedback = new FeedbackModel();
        feedback.setUserId(user.getUserId());
        feedback.setType(type);
        feedback.setSubject(subject.trim());
        feedback.setMessage(message.trim());

        try {
            if (feedbackDao.insertFeedback(feedback)) {
                request.getSession().setAttribute("successMessage", "Thank you! Your " + type.toLowerCase() + " has been submitted.");
                response.sendRedirect("memberDashboard");
            } else {
                request.setAttribute("errorMessage", "Failed to submit. Please try again.");
                request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error submitting feedback.", e);
        }
    }
}
