<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Return Bicycle — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <!-- Font Awesome & Lucide -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
    <style>
        .pm-box {
            border: 2px solid var(--clr-border-light);
            padding: 12px;
            border-radius: var(--radius-md);
            text-align: center;
            transition: all 0.2s;
            cursor: pointer;
        }
        input[type="radio"]:checked + .pm-box {
            border-color: var(--clr-primary);
            background-color: var(--clr-primary-pale);
            color: var(--clr-primary);
            font-weight: 600;
        }
    </style>
</head>
<body class="dashboard-body">

<jsp:include page="_sidebar.jsp" />

<main class="main-content">
    <div class="topbar">
        <h2>Return & Payment</h2>
    </div>

    <div class="content-wrapper" style="max-width: 600px; margin: 40px auto;">
        
        <div class="card" style="padding:0;">
            <div style="background: var(--clr-primary); color: white; padding: 20px; border-radius: var(--radius-lg) var(--radius-lg) 0 0; text-align:center;">
                <i data-lucide="check-circle" style="width: 48px; height: 48px; margin-bottom: 10px;"></i>
                <h3 style="margin: 0; font-size: 1.5rem;">Complete Your Rental</h3>
            </div>

            <div style="padding: 30px;">
                <div style="margin-bottom: 24px; border-bottom: 1px solid var(--clr-border-light); padding-bottom: 20px;">
                    <div style="display:flex; justify-content:space-between; margin-bottom:10px;">
                        <span style="color:var(--clr-text-secondary);"><i data-lucide="bike" style="width:16px;"></i> Bike</span>
                        <strong>${selectedBike.bicycleName}</strong>
                    </div>
                    <div style="display:flex; justify-content:space-between; margin-bottom:10px;">
                        <span style="color:var(--clr-text-secondary);"><i data-lucide="clock" style="width:16px;"></i> Rental Time</span>
                        <strong>${rentalHours} Hour(s)</strong>
                    </div>
                    <div style="display:flex; justify-content:space-between; margin-bottom:10px;">
                        <span style="color:var(--clr-text-secondary);"><i data-lucide="activity" style="width:16px;"></i> Hourly Rate</span>
                        <strong>NPR <fmt:formatNumber value="${selectedBike.hourlyRate}" pattern="#,##0.00"/></strong>
                    </div>
                    
                    <div style="display:flex; justify-content:space-between; margin-top:20px; padding-top:15px; border-top: 1px dashed #ddd; font-size: 1.2rem; font-weight: 700; color: var(--clr-primary);">
                        <span>Total Due:</span>
                        <span>${paymentCurrency} <fmt:formatNumber value="${paymentAmount}" pattern="#,##0.00"/></span>
                    </div>
                </div>

                <form action="${pageContext.request.contextPath}/returnBike" method="post">
                    <input type="hidden" name="recordId" value="${borrowRecord.recordId}">
                    <input type="hidden" name="paymentAmount" value="${paymentAmount}">
                    <input type="hidden" name="paymentCurrency" value="${paymentCurrency}">

                    <div style="margin-bottom: 24px;">
                        <c:choose>
                            <c:when test="${sessionScope.loggedInUser.nationality == 'Foreign'}">
                                <h4 style="margin-bottom: 12px; font-size: 0.95rem; color: var(--clr-text-secondary);"><i data-lucide="globe"></i> International Payment Method</h4>
                                <div style="display: flex; gap: 12px; flex-wrap: wrap;">
                                    <label style="flex:1;">
                                        <input type="radio" name="paymentMethod" value="CREDIT_CARD_INTL" checked style="display:none;">
                                        <div class="pm-box">
                                            <i data-lucide="credit-card" style="display:block; margin:0 auto 8px;"></i> Visa / Mastercard
                                        </div>
                                    </label>
                                    <label style="flex:1;">
                                        <input type="radio" name="paymentMethod" value="PAYPAL" style="display:none;">
                                        <div class="pm-box">
                                            <i class="fa-brands fa-paypal" style="display:block; margin:0 auto 8px; font-size:1.2rem;"></i> PayPal
                                        </div>
                                    </label>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <h4 style="margin-bottom: 12px; font-size: 0.95rem; color: var(--clr-text-secondary);"><i data-lucide="credit-card"></i> Select Payment Method</h4>
                                <div style="display: flex; gap: 12px; flex-wrap: wrap;">
                                    <label style="flex:1;">
                                        <input type="radio" name="paymentMethod" value="CASH" checked style="display:none;">
                                        <div class="pm-box">
                                            <i data-lucide="banknote" style="display:block; margin:0 auto 8px;"></i> Cash
                                        </div>
                                    </label>
                                    <label style="flex:1;">
                                        <input type="radio" name="paymentMethod" value="ESEWA" style="display:none;">
                                        <div class="pm-box">
                                            <i data-lucide="smartphone" style="display:block; margin:0 auto 8px;"></i> eSewa
                                        </div>
                                    </label>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <button type="submit" class="btn btn-primary btn-lg" style="width:100%; justify-content:center;">
                        Pay & Return Bike
                    </button>
                    <a href="${pageContext.request.contextPath}/memberDashboard" class="btn btn-ghost" style="width:100%; display:block; text-align:center; margin-top:10px;">Cancel</a>
                </form>
            </div>
        </div>

    </div>
</main>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    });
</script>
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
</body>
</html>
