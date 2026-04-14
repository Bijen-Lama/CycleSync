package com.cyclesync.controllers;

import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hashgen")
public class HashGenServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pwd = request.getParameter("pwd");

        out.println("<html><body style='font-family:monospace;padding:30px;'>");

        if (pwd == null || pwd.isEmpty()) {
            out.println("<p>Add ?pwd=YourPassword to the URL</p>");
        } else {
            try {
                String hash = BCrypt.hashpw(pwd, BCrypt.gensalt(12));
                boolean ok  = BCrypt.checkpw(pwd, hash);

                out.println("<p><b>Password :</b> " + pwd + "</p>");
                out.println("<p><b>Hash     :</b> <span id='h'>" + hash + "</span></p>");
                out.println("<p style='color:" + (ok?"green":"red") + "'><b>Verified :</b> " + ok + "</p>");
                out.println("<hr/><p><b>Run this SQL in phpMyAdmin:</b></p>");
                out.println("<pre style='background:#eee;padding:14px;'>" +
                    "UPDATE users\n" +
                    "SET    userPassword = '" + hash + "'\n" +
                    "WHERE  userEmail    = 'admin@cyclesync.com';</pre>");
            } catch (Exception e) {
                out.println("<p style='color:red'>BCrypt failed: " + e.getMessage() + "</p>");
                out.println("<p style='color:red'>jbcrypt JAR is probably missing from WEB-INF/lib</p>");
            }
        }
        out.println("</body></html>");
    }
}