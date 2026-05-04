<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Terms & Conditions - CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://unpkg.com/lucide@latest"></script>
    <style>
        .legal-container {
            max-width: 800px;
            margin: 40px auto;
            background: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
        }
        .legal-header {
            text-align: center;
            margin-bottom: 40px;
            border-bottom: 1px solid var(--clr-border-light);
            padding-bottom: 20px;
        }
        .legal-content h2 { margin-top: 30px; margin-bottom: 15px; color: var(--clr-primary); font-size: 1.4rem; }
        .legal-content p, .legal-content li { line-height: 1.7; color: var(--clr-text-secondary); margin-bottom: 15px; }
        .legal-content ul { padding-left: 20px; margin-bottom: 20px; }
        .btn-back { display: inline-flex; align-items: center; gap: 8px; color: var(--clr-primary); text-decoration: none; font-weight: 500; }
        .btn-back:hover { text-decoration: underline; }
    </style>
</head>
<body style="background: var(--clr-bg-light);">

<div class="legal-container">
    <div style="margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/login" class="btn-back"><i data-lucide="arrow-left"></i> Back</a>
    </div>

    <div class="legal-header">
        <h1>Terms & Conditions</h1>
        <p style="color: var(--clr-text-light); margin-top: 10px;">Effective Date: May 1, 2026</p>
    </div>

    <div class="legal-content">
        <h2>1. User Responsibilities</h2>
        <p>By registering for CycleSync, you agree to treat the bicycles with respect and follow all local traffic laws. Users are responsible for any damages incurred while the bicycle is under their active rental period.</p>
        
        <h2>2. Rental and Payment</h2>
        <p>Rentals are charged on a per-hour basis. Payment is collected at the end of the rental period upon returning the bicycle. For Nepalese citizens, local payment gateways or cash are acceptable. For Foreign nationals, a valid international credit card or PayPal account must be used, and charges will be converted to USD.</p>
        
        <h2>3. Account Usage Rules</h2>
        <ul>
            <li>Only the registered user may ride the bicycle.</li>
            <li>Accounts cannot be shared or transferred.</li>
            <li>Administrative impersonation may be used for support purposes, but actions taken under impersonation are logged and audited.</li>
            <li>CycleSync reserves the right to suspend accounts violating these terms.</li>
        </ul>

        <h2>4. Disclaimers</h2>
        <p>CycleSync provides bicycles "as is". We do not guarantee the constant availability of bicycles in any given city. Users assume all risk associated with riding a bicycle.</p>

        <div style="margin-top: 40px; text-align: center; color: var(--clr-text-light);">
            &copy; 2026 CycleSync Platform. All Rights Reserved.
        </div>
    </div>
</div>

<script>lucide.createIcons();</script>
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
</body>
</html>
