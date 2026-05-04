<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Privacy Policy - CycleSync</title>
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
        <h1>Privacy Policy</h1>
        <p style="color: var(--clr-text-light); margin-top: 10px;">Effective Date: May 1, 2026</p>
    </div>

    <div class="legal-content">
        <h2>1. Information We Collect</h2>
        <p>We collect your name, email, phone number, and nationality during registration. We also track your active rental history and payment logs to ensure accurate billing.</p>
        
        <h2>2. How We Use Your Information</h2>
        <ul>
            <li>To manage your account and authenticate logins.</li>
            <li>To calculate rental fees and process transactions.</li>
            <li>To provide administrative support via our impersonation system.</li>
        </ul>

        <h2>3. Location Tracking</h2>
        <p>While you are actively borrowing a bicycle, the bicycle's GPS coordinates are continuously updated and tracked in our database. This information is used to show available bicycles to other users and ensure the security of our fleet.</p>

        <h2>4. Data Security</h2>
        <p>Your password is securely hashed using BCrypt. We do not store raw credit card details; all simulated payments are securely logged in our transaction ledger.</p>

        <div style="margin-top: 40px; text-align: center; color: var(--clr-text-light);">
            &copy; 2026 CycleSync Platform. All Rights Reserved.
        </div>
    </div>
</div>

<script>lucide.createIcons();</script>
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
</body>
</html>
