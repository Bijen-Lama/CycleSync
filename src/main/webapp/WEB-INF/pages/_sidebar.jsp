<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    _sidebar.jsp — Shared sidebar navigation fragment.
    Include this in every dashboard page with:
        <%@ include file="_sidebar.jsp" %>
    Before including, set the 'activePage' request attribute or page variable.
--%>

<%
    com.cyclesync.model.UserModel sidebarUser =
        (com.cyclesync.model.UserModel) session.getAttribute("loggedInUser");
    String activePage = (String) request.getAttribute("activePage");
    if (activePage == null) activePage = "";
    String userInitial = (sidebarUser != null && sidebarUser.getFullName() != null &&
                          !sidebarUser.getFullName().isEmpty())
                         ? String.valueOf(sidebarUser.getFullName().charAt(0)).toUpperCase()
                         : "?";
%>

<aside class="sidebar">
    <a href="${pageContext.request.contextPath}/<%= sidebarUser != null && sidebarUser.isAdmin() ? "adminDashboard" : "memberDashboard" %>"
       class="sidebar-brand">
        <div class="sidebar-brand-icon">🚲</div>
        <div>
            <div class="sidebar-brand-text">CycleSync</div>
            <div class="sidebar-brand-sub">Bicycle Sharing</div>
        </div>
    </a>

    <nav class="sidebar-nav">

        <% if (sidebarUser != null && sidebarUser.isAdmin()) { %>
            <div class="sidebar-section-label">Admin Panel</div>

            <a href="${pageContext.request.contextPath}/adminDashboard"
               class="sidebar-link <%= "adminDashboard".equals(activePage) ? "active" : "" %>">
                <span class="nav-icon">🏠</span> Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/manageBicycles"
               class="sidebar-link <%= "manageBicycles".equals(activePage) ? "active" : "" %>">
                <span class="nav-icon">🚲</span> Manage Bikes
            </a>
            <a href="${pageContext.request.contextPath}/manageMembers"
               class="sidebar-link <%= "manageMembers".equals(activePage) ? "active" : "" %>">
                <span class="nav-icon">👥</span> Manage Members
            </a>
            <a href="${pageContext.request.contextPath}/fines"
               class="sidebar-link <%= "fines".equals(activePage) ? "active" : "" %>">
                <span class="nav-icon">💰</span> Fine Management
            </a>

        <% } else { %>
            <div class="sidebar-section-label">Member Portal</div>

            <a href="${pageContext.request.contextPath}/memberDashboard"
               class="sidebar-link <%= "memberDashboard".equals(activePage) ? "active" : "" %>">
                <span class="nav-icon">🏠</span> My Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/searchBicycles"
               class="sidebar-link <%= "searchBicycles".equals(activePage) ? "active" : "" %>">
                <span class="nav-icon">🔍</span> Find a Bike
            </a>
            <a href="${pageContext.request.contextPath}/ridingHistory"
               class="sidebar-link <%= "ridingHistory".equals(activePage) ? "active" : "" %>">
                <span class="nav-icon">🕐</span> Ride History
            </a>
            <a href="${pageContext.request.contextPath}/fines"
               class="sidebar-link <%= "fines".equals(activePage) ? "active" : "" %>">
                <span class="nav-icon">💰</span> My Fines
            </a>

        <% } %>

        <div class="sidebar-section-label">Account</div>
        <a href="${pageContext.request.contextPath}/logout" class="sidebar-link">
            <span class="nav-icon">🚪</span> Sign Out
        </a>

    </nav>

    <div class="sidebar-footer">
        <div class="sidebar-user">
            <div class="sidebar-avatar"><%= userInitial %></div>
            <div class="sidebar-user-info">
                <div class="sidebar-user-name">
                    <%= sidebarUser != null ? sidebarUser.getFullName() : "Unknown" %>
                </div>
                <div class="sidebar-user-role">
                    <%= sidebarUser != null ? sidebarUser.getUserRole() : "" %>
                </div>
            </div>
        </div>
    </div>
</aside>
