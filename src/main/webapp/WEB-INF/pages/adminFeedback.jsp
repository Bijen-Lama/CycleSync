<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Member Feedback — Admin — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
    <style>
        .badge-type { padding: 4px 10px; border-radius: 4px; font-size: 0.7rem; font-weight: 700; text-transform: uppercase; }
        .type-suggestion { background: #e0f2fe; color: #0369a1; }
        .type-complaint  { background: #fee2e2; color: #b91c1c; }
        
        .status-new      { color: #0891b2; background: #ecfeff; }
        .status-read     { color: #4b5563; background: #f3f4f6; }
        .status-resolved { color: #059669; background: #ecfdf5; }
    </style>
</head>
<body class="dashboard-body">
<jsp:include page="_sidebar.jsp" />

<main class="main-content">
    <div class="topbar">
        <h2><i data-lucide="message-square"></i> Member Feedback</h2>
    </div>

    <div class="content-wrapper">
        <div class="card" style="padding: 0; overflow: hidden;">
            <div style="padding: 20px 24px; border-bottom: 1px solid var(--clr-border-light); display:flex; justify-content:space-between; align-items:center;">
                <h3 style="margin: 0;">Suggestions & Complaints</h3>
                <span class="table-count">${feedbackList.size()} message(s)</span>
            </div>

            <div class="table-wrap">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Member</th>
                            <th>Type</th>
                            <th>Subject & Message</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty feedbackList}">
                                <tr><td colspan="6" style="text-align:center; padding: 40px; color: var(--clr-text-light);">No feedback received yet.</td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="f" items="${feedbackList}">
                                    <tr>
                                        <td>
                                            <div style="font-weight: 600;">${f.userName}</div>
                                            <div style="font-size: 0.78rem; color: var(--clr-text-light);">${f.userEmail}</div>
                                        </td>
                                        <td>
                                            <span class="badge-type type-${fn:toLowerCase(f.type)}">${f.type}</span>
                                        </td>
                                        <td style="max-width: 400px;">
                                            <div style="font-weight: 600; margin-bottom: 4px;">${f.subject}</div>
                                            <div style="font-size: 0.85rem; color: var(--clr-text-light); white-space: normal; line-height: 1.4;">${f.message}</div>
                                        </td>
                                        <td style="white-space: nowrap;"><fmt:formatDate value="${f.createdAt}" pattern="dd MMM, HH:mm"/></td>
                                        <td>
                                            <span class="badge status-${fn:toLowerCase(f.status)}">${f.status}</span>
                                        </td>
                                        <td>
                                            <form action="adminFeedback" method="post" style="display: inline-flex; gap: 4px;">
                                                <input type="hidden" name="feedbackId" value="${f.feedbackId}">
                                                <c:choose>
                                                    <c:when test="${f.status == 'NEW'}">
                                                        <button name="status" value="READ" class="btn btn-outline btn-sm">Mark as Read</button>
                                                    </c:when>
                                                    <c:when test="${f.status == 'READ'}">
                                                        <button name="status" value="RESOLVED" class="btn btn-primary btn-sm">Resolve</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button name="status" value="READ" class="btn btn-ghost btn-sm">Re-open</button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        if (typeof lucide !== 'undefined') lucide.createIcons();
    });
</script>
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
</body>
</html>
