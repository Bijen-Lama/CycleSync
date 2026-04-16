<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<% request.setAttribute("activePage", "searchBicycles"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Borrow Bicycle — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
</head>
<body>

<div class="layout-wrapper">
    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">
        <header class="topbar">
            <span class="topbar-title">🚲 Confirm Borrow</span>
            <div class="topbar-actions">
                <a href="${pageContext.request.contextPath}/searchBicycles" class="btn btn-ghost btn-sm">
                    ← Back to Search
                </a>
            </div>
        </header>

        <main class="page-body">

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error"><span class="alert-icon">⚠️</span> ${errorMessage}</div>
            </c:if>

            <c:choose>
                <c:when test="${empty selectedBike}">
                    <div class="empty-state">
                        <div class="empty-state-icon">❌</div>
                        <h4>Bicycle not found</h4>
                        <p><a href="${pageContext.request.contextPath}/searchBicycles">Browse available bikes →</a></p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="borrow-confirm-card">

                        <div class="borrow-confirm-hero">
                            <c:choose>
                                <c:when test="${selectedBike.bicycleType == 'MOUNTAIN'}"><span class="big-emoji">🏔</span></c:when>
                                <c:when test="${selectedBike.bicycleType == 'ROAD'}"><span class="big-emoji">🛣</span></c:when>
                                <c:when test="${selectedBike.bicycleType == 'ELECTRIC'}"><span class="big-emoji">⚡</span></c:when>
                                <c:otherwise><span class="big-emoji">🚲</span></c:otherwise>
                            </c:choose>
                            <h2>${selectedBike.bicycleName}</h2>
                            <p>Review the details below before confirming your borrow.</p>
                        </div>

                        <div class="borrow-details">

                            <div class="detail-row">
                                <span class="label">🏷 Bike Type</span>
                                <span class="value">
                                    <span class="type-tag type-${fn:toLowerCase(selectedBike.bicycleType)}">${selectedBike.bicycleType}</span>
                                </span>
                            </div>

                            <div class="detail-row">
                                <span class="label">📍 Pick-up Location</span>
                                <span class="value">${selectedBike.locationCode}</span>
                            </div>

                            <div class="detail-row">
                                <span class="label">💰 Hourly Rate</span>
                                <span class="value price">
                                    NPR <fmt:formatNumber value="${selectedBike.hourlyRate}" pattern="#,##0.00"/> / hour
                                </span>
                            </div>

                            <div class="detail-row">
                                <span class="label">⏰ Loan Duration</span>
                                <span class="value">Up to 24 hours</span>
                            </div>

                            <div class="detail-row">
                                <span class="label">⚠️ Late Fee</span>
                                <span class="value" style="color:var(--clr-danger);">NPR 350.00 per hour overdue</span>
                            </div>

                            <c:if test="${not empty selectedBike.description}">
                                <div class="detail-row">
                                    <span class="label">📝 Notes</span>
                                    <span class="value">${selectedBike.description}</span>
                                </div>
                            </c:if>

                        </div>

                        <div class="borrow-confirm-footer">
                            <form action="${pageContext.request.contextPath}/borrowBike"
                                  method="post" style="flex:1;">
                                <input type="hidden" name="bicycleId" value="${selectedBike.bicycleId}">
                                <button type="submit" class="btn btn-accent btn-lg" style="width:100%;justify-content:center;">
                                    ✅ Confirm Borrow
                                </button>
                            </form>
                            <a href="${pageContext.request.contextPath}/searchBicycles"
                               class="btn btn-ghost btn-lg" style="white-space:nowrap;">
                                Cancel
                            </a>
                        </div>

                    </div>
                </c:otherwise>
            </c:choose>

        </main>
    </div>
</div>

</body>
</html>
