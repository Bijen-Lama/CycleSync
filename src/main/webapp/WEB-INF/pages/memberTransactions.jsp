<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Transactions — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
    <style>
        .badge { display: inline-flex; align-items: center; gap: 5px; padding: 4px 10px; border-radius: 20px; font-size: 0.78rem; font-weight: 600; }
        .badge-completed { background: #dcfce7; color: #166534; }
        .badge-pending   { background: #fef9c3; color: #854d0e; }
        .badge-failed    { background: #fee2e2; color: #991b1b; }
        .tx-table { width: 100%; border-collapse: collapse; }
        .tx-table th { padding: 12px 16px; text-align: left; font-size: 0.78rem; color: var(--clr-text-light); text-transform: uppercase; letter-spacing: 0.05em; background: var(--clr-bg-light); border-bottom: 2px solid var(--clr-border-light); }
        .tx-table td { padding: 14px 16px; border-bottom: 1px solid var(--clr-border-light); font-size: 0.9rem; vertical-align: middle; }
        .tx-table tbody tr:hover { background: var(--clr-bg-light); }
        .filter-bar { display: flex; gap: 10px; margin-bottom: 20px; align-items: center; flex-wrap: wrap; }
        .filter-bar input { flex: 1; min-width: 200px; }
    </style>
</head>
<body class="dashboard-body">
<jsp:include page="_sidebar.jsp" />

<main class="main-content">
    <div class="topbar">
        <h2><i data-lucide="receipt"></i> My Transactions</h2>
    </div>

    <div class="content-wrapper">
        <!-- Flash Messages -->
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="alert alert-success"><i data-lucide="check-circle"></i> ${sessionScope.successMessage}</div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="alert alert-error"><i data-lucide="alert-triangle"></i> ${sessionScope.errorMessage}</div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>

        <div class="card" style="padding: 0; overflow: hidden;">
            <div style="padding: 20px 24px; border-bottom: 1px solid var(--clr-border-light); display:flex; justify-content:space-between; align-items:center;">
                <h3 style="margin: 0;">Payment History</h3>
                <span style="color: var(--clr-text-light); font-size: 0.85rem;">${transactions.size()} record(s)</span>
            </div>

            <!-- Filter Bar -->
            <div style="padding: 16px 24px; border-bottom: 1px solid var(--clr-border-light); background: var(--clr-bg-light);">
                <div class="filter-bar">
                    <input type="text" id="txSearch" class="form-control" placeholder="Search bike, payment method..." style="max-width: 300px;">
                    <select id="txFilter" class="form-control" style="max-width: 160px; appearance: none;">
                        <option value="">All Status</option>
                        <option value="COMPLETED">Paid</option>
                        <option value="PENDING">Pending</option>
                        <option value="FAILED">Failed</option>
                    </select>
                </div>
            </div>

            <div style="overflow-x: auto;">
                <table class="tx-table" id="txTable">
                    <thead>
                        <tr>
                            <th>Bike</th>
                            <th>Borrowed</th>
                            <th>Returned</th>
                            <th>Payment</th>
                            <th>Amount</th>
                            <th>Currency</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty transactions}">
                                <tr><td colspan="7" style="text-align:center; padding: 40px; color: var(--clr-text-light);">
                                    <i data-lucide="inbox" style="display:block; margin: 0 auto 10px; width:32px; height:32px;"></i>
                                    No transactions yet. Borrow a bike to get started!
                                </td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="tx" items="${transactions}">
                                    <tr class="tx-row" data-status="${tx.status}">
                                        <td>${tx.bicycleName}</td>
                                        <td><fmt:formatDate value="${tx.borrowDate}" pattern="dd MMM, HH:mm"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty tx.returnDate}"><fmt:formatDate value="${tx.returnDate}" pattern="dd MMM, HH:mm"/></c:when>
                                                <c:otherwise><span style="color: var(--clr-warning);">Active</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${not empty tx.paymentMethod ? tx.paymentMethod : '—'}</td>
                                        <td><strong><fmt:formatNumber value="${tx.amount}" pattern="#,##0.00"/></strong></td>
                                        <td>${tx.currency}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${tx.status == 'COMPLETED'}"><span class="badge badge-completed"><i data-lucide="check-circle" style="width:12px;"></i> Paid</span></c:when>
                                                <c:when test="${tx.status == 'PENDING'}"><span class="badge badge-pending"><i data-lucide="clock" style="width:12px;"></i> Pending</span></c:when>
                                                <c:otherwise><span class="badge badge-failed"><i data-lucide="x-circle" style="width:12px;"></i> Failed</span></c:otherwise>
                                            </c:choose>
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

    const searchInput = document.getElementById('txSearch');
    const filterSelect = document.getElementById('txFilter');
    
    function applyFilters() {
        const q = searchInput.value.toLowerCase();
        const statusFilter = filterSelect.value.toUpperCase();
        document.querySelectorAll('.tx-row').forEach(row => {
            const text = row.textContent.toLowerCase();
            const status = row.dataset.status;
            const matchText = text.includes(q);
            const matchStatus = !statusFilter || status === statusFilter;
            row.style.display = (matchText && matchStatus) ? '' : 'none';
        });
    }

    searchInput.addEventListener('input', applyFilters);
    filterSelect.addEventListener('change', applyFilters);
});
</script>
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
</body>
</html>
