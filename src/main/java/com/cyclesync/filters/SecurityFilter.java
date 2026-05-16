package com.cyclesync.filters;

import com.cyclesync.model.UserModel;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * SecurityFilter - Intercepts all requests to handle authentication and prevent caching.
 */
@WebFilter("/*")
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // 1. Prevent Browser Caching
        // These headers ensure the browser requests the page from the server every time,
        // rather than loading a cached copy (which fixes the "Back" button issue).
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        String path = request.getRequestURI().substring(request.getContextPath().length());

        // Define public resources that don't require authentication
        boolean isPublicPage = path.equals("/") ||
                               path.equals("/login") ||
                               path.equals("/register") ||
                               path.equals("/forgot-password") ||
                               path.equals("/reset-password") ||
                               path.equals("/about") ||
                               path.equals("/terms") ||
                               path.equals("/privacy") ||
                               path.equals("/contact") ||
                               path.startsWith("/css/") ||
                               path.startsWith("/js/") ||
                               path.startsWith("/images/");

        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (!isLoggedIn && !isPublicPage) {
            // User is not logged in and trying to access a protected page
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // User is logged in OR accessing a public page
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
