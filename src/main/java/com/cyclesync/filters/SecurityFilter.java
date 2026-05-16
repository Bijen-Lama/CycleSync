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

        String path = request.getServletPath();
        String uri = request.getRequestURI();

        // 1. Prevent Browser Caching for ALL responses
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // 2. Session Validation
        boolean isLoggedIn = (session != null && session.getAttribute("loggedInUser") != null);
        
        // Define public paths
        boolean isStaticResource = uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/") || uri.contains("/lib/");
        boolean isPublicServlet = path.equals("/login") || 
                                  path.equals("/register") || 
                                  path.equals("/forgot-password") || 
                                  path.equals("/reset-password") ||
                                  path.equals("/about") ||
                                  path.equals("/contact") ||
                                  path.equals("/terms") ||
                                  path.equals("/privacy") ||
                                  path.equals("/index.jsp") ||
                                  path.equals(""); // Root path

        if (!isLoggedIn && !isPublicServlet && !isStaticResource) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
