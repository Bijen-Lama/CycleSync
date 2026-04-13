<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<% request.setAttribute("activePage", "ridingHistory"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ride History — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables.css">
</head>
<body>

<div class="layout-wrapper">
    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">
        <header class="topbar">
            <span class="topbar-title">🕐 My Ride History</span>
        </header>

        <main class="page-body">

            <div class="page-header">
                <h2>All Your Rides</h2>
                <p>A full log of every bicycle you've borrowed through CycleSync.</p>
            </div>

            <div class="table-toolbar">
                <span class="table-count">${not empty historyList ? historyList.size() : 0} total rides</span>
            </div>

            <div class="table-wrap">
                <c:choose>
                    <c:when test="${empty historyList}">
                        <div class="empty-state">
                            <div class="empty-state-icon">🛤️</div>
                            <h4>No rides yet</h4>
                            <p>Your history will appear here after your first borrow.</p>
                            <a href="${pageContext.request.contextPath}/searchBicycles"
                               class="btn btn-primary" style="margin-top:14px;">
                                Find a Bike →
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Bicycle</th>
                                    <th>Borrow Date</th>
                                    <th>Due Date</th>
                                    <th>Return Date</th>
                                    <th>Duration</th>
                                    <th>Status</th>
                                    <th>Total Cost</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="record" items="${historyList}">
                                    <tr>
                                        <td class="td-id">#${record.recordId}</td>
                                        <td>
                                            <div class="td-name">${record.bicycleName}</div>
                                            <div class="td-meta">
                                                <span class="type-tag type-${fn:toLowerCase(record.bicycleType)}">${record.bicycleType}</span>
                                            </div>
                                        </td>
                                        <td class="td-nowrap">
                                            <fmt:formatDate value="${record.borrowDate}" pattern="dd MMM yyyy"/>
                                            <div class="td-meta"><fmt:formatDate value="${record.borrowDate}" pattern="HH:mm"/></div>
                                        </td>
                                        <td class="td-nowrap">
                                            <fmt:formatDate value="${record.dueDate}" pattern="dd MMM yyyy"/>
                                            <div class="td-meta"><fmt:formatDate value="${record.dueDate}" pattern="HH:mm"/></div>
                                        </td>
                                        <td class="td-nowrap">
                                            <c:choose>
                                                <c:when test="${not empty record.returnDate}">
                                                    <fmt:formatDate value="${record.returnDate}" pattern="dd MMM yyyy"/>
                                                    <div class="td-meta"><fmt:formatDate value="${record.returnDate}" pattern="HH:mm"/></div>
                                                </c:when>
                                                <c:otherwise><span style="color:var(--clr-text-muted);">Not returned</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty record.totalHours}">
                                                    <fmt:formatNumber value="${record.totalHours}" pattern="#,##0.0"/> hrs
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
                                                    <strong style="color:var(--clr-primary);">
                                                        $<fmt:formatNumber value="${record.totalCost}" pattern="#,##0.00"/>
                                                    </strong>
                                                </c:when>
                                                <c:otherwise><span style="color:var(--clr-text-muted);">—</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>

        </main>
    </div>
</div>

</body>
</html>
