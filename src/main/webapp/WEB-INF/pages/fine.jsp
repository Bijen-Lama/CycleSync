<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<% request.setAttribute("activePage", "fines"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fines — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tables.css">
    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>

<div class="layout-wrapper">
    <%@ include file="_sidebar.jsp" %>

    <div class="main-content">
        <header class="topbar">
            <span class="topbar-title"><i data-lucide="wallet"></i> Fine Management</span>
            <c:if test="${sessionScope.loggedInUser.admin}">
                <div class="topbar-actions">
                    <span class="topbar-badge"><i data-lucide="alert-triangle"></i> ${pendingCount} Pending</span>
                </div>
            </c:if>
        </header>

        <main class="page-body">

            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success"><span class="alert-icon"><i data-lucide="check-circle-2"></i></span> ${sessionScope.successMessage}</div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-error"><span class="alert-icon"><i data-lucide="alert-triangle"></i></span> ${sessionScope.errorMessage}</div>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>

            <div class="page-header">
                <c:choose>
                    <c:when test="${sessionScope.loggedInUser.admin}">
                        <h2>All System Fines</h2>
                        <p>Review, mark as paid, or waive outstanding fines.</p>
                    </c:when>
                    <c:otherwise>
                        <h2>My Fines</h2>
                        <p>View your outstanding and resolved fines.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Member outstanding balance -->
            <c:if test="${not sessionScope.loggedInUser.admin and totalPending > 0}">
                <div class="fine-summary">
                    <div class="fine-summary-left">
                        <span class="fine-summary-icon"><i data-lucide="alert-triangle"></i></span>
                        <div>
                            <div class="fine-summary-label">Total Outstanding Balance</div>
                            <div class="fine-summary-amount">NPR <fmt:formatNumber value="${totalPending}" pattern="#,##0.00"/></div>
                        </div>
                    </div>
                    <span class="badge badge-danger" style="font-size:.8rem;">Action Required</span>
                </div>
            </c:if>

            <!-- Fines Table -->
            <div class="table-wrap">
                <c:choose>
                    <c:when test="${empty fineList}">
                        <div class="empty-state">
                            <div class="empty-state-icon"><i data-lucide="check-circle-2"></i></div>
                            <h4>No fines found</h4>
                            <p>
                                <c:choose>
                                    <c:when test="${sessionScope.loggedInUser.admin}">No fines have been issued yet.</c:when>
                                    <c:otherwise>Great news — you have no fines! Keep returning bikes on time.</c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <c:if test="${sessionScope.loggedInUser.admin}"><th>Member</th></c:if>
                                    <th>Bicycle</th>
                                    <th>Reason</th>
                                    <th>Amount</th>
                                    <th>Issued</th>
                                    <th>Status</th>
                                    <c:if test="${sessionScope.loggedInUser.admin}"><th>Actions</th></c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="fine" items="${fineList}">
                                    <tr>
                                        <td class="td-id">#${fine.fineId}</td>

                                        <c:if test="${sessionScope.loggedInUser.admin}">
                                            <td>
                                                <div class="td-name">${fine.memberName}</div>
                                                <div class="td-meta">${fine.memberEmail}</div>
                                            </td>
                                        </c:if>

                                        <td class="td-name">${fine.bicycleName}</td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${fine.fineReason == 'LATE_RETURN'}">
                                                    <span class="badge badge-warning">⏰ Late Return</span>
                                                </c:when>
                                                <c:when test="${fine.fineReason == 'DAMAGE'}">
                                                    <span class="badge badge-danger"><i data-lucide="wrench"></i> Damage</span>
                                                </c:when>
                                                <c:when test="${fine.fineReason == 'LOSS'}">
                                                    <span class="badge badge-danger">❌ Loss</span>
                                                </c:when>
                                            </c:choose>
                                        </td>

                                        <td>
                                            <strong style="color:var(--clr-danger);">
                                                NPR <fmt:formatNumber value="${fine.fineAmount}" pattern="#,##0.00"/>
                                            </strong>
                                        </td>

                                        <td class="td-nowrap">
                                            <fmt:formatDate value="${fine.issuedAt}" pattern="dd MMM yyyy"/>
                                        </td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${fine.fineStatus == 'PENDING'}">
                                                    <span class="badge badge-danger">Pending</span>
                                                </c:when>
                                                <c:when test="${fine.fineStatus == 'PAID'}">
                                                    <span class="badge badge-success">Paid</span>
                                                </c:when>
                                                <c:when test="${fine.fineStatus == 'WAIVED'}">
                                                    <span class="badge badge-muted">Waived</span>
                                                </c:when>
                                            </c:choose>
                                        </td>

                                        <c:if test="${sessionScope.loggedInUser.admin}">
                                            <td>
                                                <c:if test="${fine.fineStatus == 'PENDING'}">
                                                    <div class="td-actions">
                                                        <form action="${pageContext.request.contextPath}/fines"
                                                              method="post" style="display:inline;">
                                                            <input type="hidden" name="fineId" value="${fine.fineId}">
                                                            <input type="hidden" name="action" value="markPaid">
                                                            <input type="hidden" name="adminNotes" value="Marked paid by admin.">
                                                            <button type="submit" class="btn btn-accent btn-sm"><i data-lucide="check-circle-2"></i> Mark Paid</button>
                                                        </form>
                                                        <form action="${pageContext.request.contextPath}/fines"
                                                              method="post" style="display:inline;"
                                                              onsubmit="return confirm('Waive this fine?');">
                                                            <input type="hidden" name="fineId" value="${fine.fineId}">
                                                            <input type="hidden" name="action" value="waive">
                                                            <input type="hidden" name="adminNotes" value="Waived by admin.">
                                                            <button type="submit" class="btn btn-ghost btn-sm"><i data-lucide="arrow-left-right"></i> Waive</button>
                                                        </form>
                                                    </div>
                                                </c:if>
                                                <c:if test="${fine.fineStatus != 'PENDING'}">
                                                    <span style="color:var(--clr-text-muted);font-size:.8rem;">Resolved</span>
                                                </c:if>
                                            </td>
                                        </c:if>
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


<script>
    document.addEventListener("DOMContentLoaded", function() {
        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    });
</script>
</body>
</html>
