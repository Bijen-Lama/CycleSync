<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<% request.setAttribute("activePage", "adminDashboard"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables.css">
</head>
<body>

<div class="layout-wrapper">

    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">

        <!-- Topbar -->
        <header class="topbar">
            <span class="topbar-title">Admin Dashboard</span>
            <div class="topbar-actions">
                <span class="topbar-badge">🟢 System Online</span>
            </div>
        </header>

        <main class="page-body">

            <!-- Flash Messages -->
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success">
                    <span class="alert-icon">✅</span> ${sessionScope.successMessage}
                </div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-error">
                    <span class="alert-icon">⚠️</span> ${sessionScope.errorMessage}
                </div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <!-- Page Header -->
            <div class="page-header">
                <h2>Good to see you, ${sessionScope.loggedInUser.fullName} 👋</h2>
                <p>Here's a live overview of the CycleSync fleet and operations.</p>
            </div>

            <!-- Stats Grid -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon green">🚲</div>
                    <div class="stat-info">
                        <div class="stat-value">${totalAvailable}</div>
                        <div class="stat-label">Available Bikes</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon blue">🔄</div>
                    <div class="stat-info">
                        <div class="stat-value">${totalBorrowed}</div>
                        <div class="stat-label">Currently Borrowed</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon orange">🔧</div>
                    <div class="stat-info">
                        <div class="stat-value">${totalMaintenance}</div>
                        <div class="stat-label">In Maintenance</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon primary">👥</div>
                    <div class="stat-info">
                        <div class="stat-value">${totalMembers}</div>
                        <div class="stat-label">Total Members</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon info">📋</div>
                    <div class="stat-info">
                        <div class="stat-value">${activeLoans}</div>
                        <div class="stat-label">Active Loans</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon red">💰</div>
                    <div class="stat-info">
                        <div class="stat-value">${pendingFines}</div>
                        <div class="stat-label">Pending Fines</div>
                    </div>
                </div>
            </div>

            <!-- Quick Actions -->
            <div class="card" style="margin-bottom:20px;">
                <div class="card-header">
                    <h3>⚡ Quick Actions</h3>
                </div>
                <div class="card-body" style="display:flex;gap:12px;flex-wrap:wrap;">
                    <a href="${pageContext.request.contextPath}/manageBicycles" class="btn btn-primary">
                        🚲 Manage Bikes
                    </a>
                    <a href="${pageContext.request.contextPath}/manageMembers" class="btn btn-outline">
                        👥 Manage Members
                    </a>
                    <a href="${pageContext.request.contextPath}/fines" class="btn btn-outline">
                        💰 View Fines
                    </a>
                </div>
            </div>

            <!-- Recent Borrow Records -->
            <div class="card">
                <div class="card-header">
                    <h3>📋 Recent Borrow Records</h3>
                    <span class="badge badge-info">Live</span>
                </div>

                <div class="table-wrap" style="border:none;border-radius:0;box-shadow:none;">
                    <c:choose>
                        <c:when test="${empty recentRecords}">
                            <div class="empty-state">
                                <div class="empty-state-icon">📭</div>
                                <h4>No borrow records yet</h4>
                                <p>Records will appear here once members start borrowing bikes.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Member</th>
                                        <th>Bicycle</th>
                                        <th>Borrow Date</th>
                                        <th>Due Date</th>
                                        <th>Return Date</th>
                                        <th>Status</th>
                                        <th>Cost</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="record" items="${recentRecords}" varStatus="loop">
                                        <c:if test="${loop.index < 15}">
                                        <tr>
                                            <td class="td-id">#${record.recordId}</td>
                                            <td>
                                                <div class="td-name">${record.memberName}</div>
                                                <div class="td-meta">${record.memberEmail}</div>
                                            </td>
                                            <td>
                                                <div class="td-name">${record.bicycleName}</div>
                                                <div class="td-meta">
                                                    <span class="type-tag type-${fn:toLowerCase(record.bicycleType)}">${record.bicycleType}</span>
                                                </div>
                                            </td>
                                            <td class="td-nowrap">
                                                <fmt:formatDate value="${record.borrowDate}" pattern="dd MMM yyyy HH:mm"/>
                                            </td>
                                            <td class="td-nowrap">
                                                <fmt:formatDate value="${record.dueDate}" pattern="dd MMM yyyy HH:mm"/>
                                            </td>
                                            <td class="td-nowrap">
                                                <c:choose>
                                                    <c:when test="${not empty record.returnDate}">
                                                        <fmt:formatDate value="${record.returnDate}" pattern="dd MMM yyyy HH:mm"/>
                                                    </c:when>
                                                    <c:otherwise><span style="color:var(--clr-text-muted);">—</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
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
                                                    <c:otherwise>
                                                        <span class="badge badge-muted">${record.recordStatus}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty record.totalCost}">
                                                        <strong>$<fmt:formatNumber value="${record.totalCost}" pattern="#,##0.00"/></strong>
                                                    </c:when>
                                                    <c:otherwise><span style="color:var(--clr-text-muted);">—</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </main>
    </div>
</div>

</body>
</html>
