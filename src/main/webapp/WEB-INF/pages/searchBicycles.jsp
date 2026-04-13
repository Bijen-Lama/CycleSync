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
    <title>Find a Bike — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
</head>
<body>

<div class="layout-wrapper">
    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">
        <header class="topbar">
            <span class="topbar-title">🔍 Find a Bike</span>
        </header>

        <main class="page-body">

            <div class="page-header">
                <h2>Available Bicycles</h2>
                <p>Browse and borrow from our fleet. Filter by bike type to find your perfect ride.</p>
            </div>

            <!-- Filter Row -->
            <form action="${pageContext.request.contextPath}/searchBicycles" method="get">
                <div class="filter-row">
                    <label>Filter by type:</label>
                    <select name="bicycleType" class="form-control" onchange="this.form.submit()" style="max-width:180px;">
                        <option value="ALL"      ${selectedType == 'ALL'      ? 'selected' : ''}>All Types</option>
                        <option value="MOUNTAIN" ${selectedType == 'MOUNTAIN' ? 'selected' : ''}>🏔 Mountain</option>
                        <option value="ROAD"     ${selectedType == 'ROAD'     ? 'selected' : ''}>🛣 Road</option>
                        <option value="ELECTRIC" ${selectedType == 'ELECTRIC' ? 'selected' : ''}>⚡ Electric</option>
                        <option value="HYBRID"   ${selectedType == 'HYBRID'   ? 'selected' : ''}>🔀 Hybrid</option>
                    </select>

                    <span class="table-count" style="margin-left:auto;">
                        ${not empty bikeResults ? bikeResults.size() : 0} bike(s) available
                    </span>
                </div>
            </form>

            <!-- Results Grid -->
            <c:choose>
                <c:when test="${empty bikeResults}">
                    <div class="empty-state" style="background:var(--clr-surface);border:1px solid var(--clr-border-light);border-radius:var(--radius-lg);box-shadow:var(--shadow-sm);">
                        <div class="empty-state-icon">🔍</div>
                        <h4>No bikes available</h4>
                        <p>No bikes match your filter right now. Try a different type or check back later.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="bikes-grid">
                        <c:forEach var="bike" items="${bikeResults}">
                            <div class="bike-card">
                                <div class="bike-card-header">
                                    <c:choose>
                                        <c:when test="${bike.bicycleType == 'MOUNTAIN'}"><span class="bike-card-emoji">🏔</span></c:when>
                                        <c:when test="${bike.bicycleType == 'ROAD'}"><span class="bike-card-emoji">🛣</span></c:when>
                                        <c:when test="${bike.bicycleType == 'ELECTRIC'}"><span class="bike-card-emoji">⚡</span></c:when>
                                        <c:otherwise><span class="bike-card-emoji">🚲</span></c:otherwise>
                                    </c:choose>
                                    <div class="bike-card-name">${bike.bicycleName}</div>
                                    <span class="bike-card-status-badge status-available">Available</span>
                                </div>
                                <div class="bike-card-body">
                                    <div class="bike-meta-row">
                                        <span class="meta-icon">🏷</span>
                                        <span class="type-tag type-${fn:toLowerCase(bike.bicycleType)}">${bike.bicycleType}</span>
                                    </div>
                                    <div class="bike-meta-row">
                                        <span class="meta-icon">📍</span>
                                        ${bike.locationCode}
                                    </div>
                                    <c:if test="${not empty bike.description}">
                                        <div class="bike-meta-row" style="align-items:flex-start;">
                                            <span class="meta-icon">📝</span>
                                            <span>${bike.description}</span>
                                        </div>
                                    </c:if>
                                    <div class="bike-rate">
                                        $<fmt:formatNumber value="${bike.hourlyRate}" pattern="#,##0.00"/>
                                        <span>/ hour</span>
                                    </div>
                                </div>
                                <div class="bike-card-footer">
                                    <a href="${pageContext.request.contextPath}/borrowBike?bicycleId=${bike.bicycleId}"
                                       class="btn btn-primary" style="width:100%;justify-content:center;">
                                        Borrow This Bike →
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>

        </main>
    </div>
</div>

</body>
</html>
