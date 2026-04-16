<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% request.setAttribute("activePage", "memberDashboard"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Dashboard — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables.css">
</head>
<body>

<div class="layout-wrapper">
    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">
        <header class="topbar">
            <span class="topbar-title">My Dashboard</span>
            <div class="topbar-actions">
                <a href="${pageContext.request.contextPath}/searchBicycles" class="btn btn-primary btn-sm">
                    🔍 Find a Bike
                </a>
            </div>
        </header>

        <main class="page-body">

            <!-- Flash Messages -->
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success"><span class="alert-icon">✅</span> ${sessionScope.successMessage}</div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-error"><span class="alert-icon">⚠️</span> ${sessionScope.errorMessage}</div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <div class="page-header">
                <h2>Welcome back, ${sessionScope.loggedInUser.fullName} 🚴</h2>
                <p>Here's what's happening with your CycleSync account today.</p>
            </div>

            <!-- Pending Fine Banner -->
            <c:if test="${totalPendingFine > 0}">
                <div class="fine-summary">
                    <div class="fine-summary-left">
                        <span class="fine-summary-icon">⚠️</span>
                        <div>
                            <div class="fine-summary-label">Outstanding Fine Balance</div>
                            <div class="fine-summary-amount">NPR <fmt:formatNumber value="${totalPendingFine}" pattern="#,##0.00"/></div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/fines" class="btn btn-danger btn-sm">View Fines →</a>
                </div>
            </c:if>

            <!-- Dashboard Grid -->
            <div class="dash-grid">

                <!-- Main Column -->
                <div>

                    <!-- Active Borrow -->
                    <div class="card" style="margin-bottom:20px;">
                        <div class="card-header">
                            <h3>🚲 Current Borrow</h3>
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${not empty activeBorrow}">
                                    <div class="active-borrow-panel">
                                        <div class="abp-label">Currently Riding</div>
                                        <div class="abp-bike-name">${activeBorrow.bicycleName}</div>

                                        <div class="abp-detail-row">
                                            <span class="abp-detail-label">🗂 Type</span>
                                            <span class="abp-detail-value">${activeBorrow.bicycleType}</span>
                                        </div>
                                        <div class="abp-detail-row">
                                            <span class="abp-detail-label">📅 Borrowed On</span>
                                            <span class="abp-detail-value">
                                                <fmt:formatDate value="${activeBorrow.borrowDate}" pattern="dd MMM yyyy, HH:mm"/>
                                            </span>
                                        </div>
                                        <div class="abp-detail-row">
                                            <span class="abp-detail-label">⏰ Return By</span>
                                            <span class="abp-detail-value">
                                                <fmt:formatDate value="${activeBorrow.dueDate}" pattern="dd MMM yyyy, HH:mm"/>
                                            </span>
                                        </div>

                                        <div class="abp-due-warning">
                                            ⚠️ Late returns are fined NPR 350.00/hour after the due date.
                                        </div>

                                        <form action="${pageContext.request.contextPath}/returnBike"
                                              method="post"
                                              onsubmit="return confirm('Confirm return of this bicycle?');">
                                            <input type="hidden" name="recordId" value="${activeBorrow.recordId}">
                                            <button type="submit" class="abp-return-btn">
                                                ✅ Return This Bicycle
                                            </button>
                                        </form>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="no-borrow-panel">
                                        <div class="icon">🛤️</div>
                                        <h4>No active borrow</h4>
                                        <p>You're not currently borrowing any bicycle.</p>
                                        <a href="${pageContext.request.contextPath}/searchBicycles"
                                           class="btn btn-primary" style="margin-top:14px;">
                                            Find Available Bikes →
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <!-- Recent History -->
                    <div class="card">
                        <div class="card-header">
                            <h3>🕐 Recent Ride History</h3>
                            <a href="${pageContext.request.contextPath}/ridingHistory"
                               class="btn btn-ghost btn-sm">View All →</a>
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${empty recentHistory}">
                                    <div class="empty-state" style="padding:32px 0;">
                                        <div class="empty-state-icon">📭</div>
                                        <h4>No rides yet</h4>
                                        <p>Your ride history will appear here.</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="timeline">
                                        <c:forEach var="record" items="${recentHistory}" varStatus="loop">
                                            <c:if test="${loop.index < 5}">
                                            <div class="timeline-item">
                                                <div class="timeline-dot-wrap">
                                                    <div class="timeline-dot ${fn:toLowerCase(record.recordStatus)}"></div>
                                                    <c:if test="${!loop.last}"><div class="timeline-line"></div></c:if>
                                                </div>
                                                <div class="timeline-content">
                                                    <div class="timeline-title">
                                                        ${record.bicycleName}
                                                        <c:choose>
                                                            <c:when test="${record.recordStatus == 'RETURNED'}">
                                                                <span class="badge badge-success">Returned</span>
                                                            </c:when>
                                                            <c:when test="${record.recordStatus == 'ACTIVE'}">
                                                                <span class="badge badge-info">Active</span>
                                                            </c:when>
                                                            <c:when test="${record.recordStatus == 'OVERDUE'}">
                                                                <span class="badge badge-danger">Overdue</span>
                                                            </c:when>
                                                        </c:choose>
                                                    </div>
                                                    <div class="timeline-meta">
                                                        <span>📅 <fmt:formatDate value="${record.borrowDate}" pattern="dd MMM yyyy"/></span>
                                                        <c:if test="${not empty record.totalHours}">
                                                            <span>⏱ <fmt:formatNumber value="${record.totalHours}" pattern="#,##0.0"/> hrs</span>
                                                        </c:if>
                                                    </div>
                                                </div>
                                                <c:if test="${not empty record.totalCost}">
                                                    <div class="timeline-cost">
                                                        NPR <fmt:formatNumber value="${record.totalCost}" pattern="#,##0.00"/>
                                                    </div>
                                                </c:if>
                                            </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                </div>

                <!-- Side Column -->
                <div>

                    <!-- Quick Stats -->
                    <div class="card" style="margin-bottom:20px;">
                        <div class="card-header"><h3>📊 My Stats</h3></div>
                        <div class="card-body" style="display:flex;flex-direction:column;gap:14px;">
                            <div class="stat-card" style="box-shadow:none;border:1px solid var(--clr-border-light);">
                                <div class="stat-icon primary">🚲</div>
                                <div class="stat-info">
                                    <div class="stat-value">${recentHistory.size()}</div>
                                    <div class="stat-label">Total Rides</div>
                                </div>
                            </div>
                            <div class="stat-card" style="box-shadow:none;border:1px solid var(--clr-border-light);">
                                <div class="stat-icon ${totalPendingFine > 0 ? 'red' : 'green'}">💰</div>
                                <div class="stat-info">
                                    <div class="stat-value">NPR <fmt:formatNumber value="${totalPendingFine}" pattern="#,##0.00"/></div>
                                    <div class="stat-label">Pending Fines</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Quick Links -->
                    <div class="card">
                        <div class="card-header"><h3>⚡ Quick Links</h3></div>
                        <div class="card-body" style="display:flex;flex-direction:column;gap:10px;">
                            <a href="${pageContext.request.contextPath}/searchBicycles"
                               class="btn btn-primary" style="justify-content:flex-start;">
                                🔍 Find Available Bikes
                            </a>
                            <a href="${pageContext.request.contextPath}/ridingHistory"
                               class="btn btn-outline" style="justify-content:flex-start;">
                                🕐 Full Ride History
                            </a>
                            <a href="${pageContext.request.contextPath}/fines"
                               class="btn btn-outline" style="justify-content:flex-start;">
                                💰 My Fines
                            </a>
                        </div>
                    </div>

                </div>
            </div>

        </main>
    </div>
</div>

</body>
</html>
