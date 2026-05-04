<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Transactions — CycleSync Admin</title>
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
        .badge-nepalese  { background: #e0e7ff; color: #3730a3; }
        .badge-foreign   { background: #fce7f3; color: #9d174d; }
        .tx-table { width: 100%; border-collapse: collapse; font-size: 0.88rem; }
        .tx-table th { padding: 12px 14px; text-align: left; font-size: 0.75rem; color: var(--clr-text-light); text-transform: uppercase; letter-spacing: 0.05em; background: var(--clr-bg-light); border-bottom: 2px solid var(--clr-border-light); }
        .tx-table td { padding: 12px 14px; border-bottom: 1px solid var(--clr-border-light); vertical-align: middle; }
        .tx-table tbody tr:hover { background: var(--clr-bg-light); }
        .stat-card { background: white; border-radius: var(--radius-lg); border: 1px solid var(--clr-border-light); padding: 20px 24px; }
        .stat-card .stat-label { font-size: 0.8rem; color: var(--clr-text-light); margin-bottom: 6px; text-transform: uppercase; letter-spacing: 0.05em; }
        .stat-card .stat-value { font-size: 1.8rem; font-weight: 700; color: var(--clr-text-primary); }
    </style>
</head>
<body class="dashboard-body">
<jsp:include page="_sidebar.jsp" />

<main class="main-content">
    <div class="topbar">
        <h2><i data-lucide="bar-chart-3"></i> Transaction Management</h2>
    </div>

    <div class="content-wrapper">
        <!-- Revenue Stats Row -->
        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 16px; margin-bottom: 24px;">
            <div class="stat-card">
                <div class="stat-label"><i data-lucide="trending-up" style="width:14px;"></i> Total Revenue (NPR)</div>
                <div class="stat-value">NPR <fmt:formatNumber value="${totalRevenue}" pattern="#,##0.00"/></div>
            </div>
            <div class="stat-card">
                <div class="stat-label"><i data-lucide="receipt" style="width:14px;"></i> Total Transactions</div>
                <div class="stat-value">${transactions.size()}</div>
            </div>
        </div>

        <div class="card" style="padding: 0; overflow: hidden;">
            <div style="padding: 20px 24px; border-bottom: 1px solid var(--clr-border-light);">
                <h3 style="margin: 0;">All Payment Records</h3>
            </div>

            <!-- Filter Bar -->
            <div style="padding: 14px 24px; border-bottom: 1px solid var(--clr-border-light); background: var(--clr-bg-light);">
                <div style="display: flex; gap: 10px; flex-wrap: wrap; align-items: center;">
                    <input type="text" id="txSearch" class="form-control" placeholder="Search user, bike, payment..." style="max-width: 280px;">
                    <select id="txStatusFilter" class="form-control" style="max-width: 150px; appearance: none;">
                        <option value="">All Status</option>
                        <option value="COMPLETED">Paid</option>
                        <option value="PENDING">Pending</option>
                        <option value="FAILED">Failed</option>
                    </select>
                    <select id="txNatFilter" class="form-control" style="max-width: 160px; appearance: none;">
                        <option value="">All Nationalities</option>
                        <option value="Nepalese">Nepalese</option>
                        <option value="Foreign">Foreign</option>
                    </select>
                </div>
            </div>

            <div style="overflow-x: auto;">
                <table class="tx-table" id="txTable">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Member</th>
                            <th>Nationality</th>
                            <th>Bike</th>
                            <th>Borrowed</th>
                            <th>Returned</th>
                            <th>Payment</th>
                            <th>Amount</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty transactions}">
                                <tr><td colspan="9" style="text-align:center; padding: 40px; color: var(--clr-text-light);">
                                    <i data-lucide="inbox" style="display:block; margin: 0 auto 10px; width:32px; height:32px;"></i>
                                    No transactions found.
                                </td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="tx" items="${transactions}">
                                    <tr class="tx-row" data-status="${tx.status}" data-nat="${tx.memberNationality}">
                                        <td style="color: var(--clr-text-light);">#${tx.transactionId}</td>
                                        <td>
                                            <div style="font-weight: 600;">${tx.memberName}</div>
                                            <div style="font-size: 0.78rem; color: var(--clr-text-light);">${tx.memberEmail}</div>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${tx.memberNationality == 'Nepalese'}"><span class="badge badge-nepalese">🇳🇵 Nepalese</span></c:when>
                                                <c:otherwise><span class="badge badge-foreign">🌍 ${tx.memberNationality}</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${tx.bicycleName}</td>
                                        <td><fmt:formatDate value="${tx.borrowDate}" pattern="dd MMM yy, HH:mm"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty tx.returnDate}"><fmt:formatDate value="${tx.returnDate}" pattern="dd MMM yy, HH:mm"/></c:when>
                                                <c:otherwise><span style="color: var(--clr-warning); font-weight:600;">Active</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${not empty tx.paymentMethod ? tx.paymentMethod : '—'}</td>
                                        <td>
                                            <strong><fmt:formatNumber value="${tx.amount}" pattern="#,##0.00"/></strong>
                                            <span style="font-size:0.78rem; color: var(--clr-text-light);"> ${tx.currency}</span>
                                        </td>
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
    const statusFilter = document.getElementById('txStatusFilter');
    const natFilter = document.getElementById('txNatFilter');

    function applyFilters() {
        const q = searchInput.value.toLowerCase();
        const status = statusFilter.value.toUpperCase();
        const nat = natFilter.value;

        document.querySelectorAll('.tx-row').forEach(row => {
            const text = row.textContent.toLowerCase();
            const rowStatus = row.dataset.status;
            const rowNat = row.dataset.nat;
            const matchText = text.includes(q);
            const matchStatus = !status || rowStatus === status;
            const matchNat = !nat || rowNat === nat;
            row.style.display = (matchText && matchStatus && matchNat) ? '' : 'none';
        });
    }

    searchInput.addEventListener('input', applyFilters);
    statusFilter.addEventListener('change', applyFilters);
    natFilter.addEventListener('change', applyFilters);
});
</script>
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
</body>
</html>
