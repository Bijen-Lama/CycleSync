package com.cyclesync.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * SecurityFilter - Prevents browser caching (fixes back button issue)
 * and handles basic session verification for protected routes.
 */
@WebFilter("/*")
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String path = request.getRequestURI().substring(request.getContextPath().length());

        // 1. Prevent Browser Caching for ALL responses
        // This ensures the back button doesn't show sensitive data after logout
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        // 2. Session Validation for protected routes
        // Allow access to login, register, forgot-password, and static assets
        boolean isStaticResource = path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/");
        boolean isPublicPage = path.equals("/login") || 
                               path.equals("/register") || 
                               path.equals("/forgot-password") || 
                               path.equals("/reset-password") ||
                               path.equals("/about") ||
                               path.equals("/contact") ||
                               path.equals("/terms") ||
                               path.equals("/privacy") ||
                               path.equals("/") ||
                               path.equals("/index.jsp");

        boolean isLoggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (!isLoggedIn && !isPublicPage && !isStaticResource) {
            // User is not logged in and trying to access a protected page
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // Continue the request
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
