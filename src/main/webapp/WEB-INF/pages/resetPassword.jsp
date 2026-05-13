<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
    <!-- Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>

<div class="auth-page">

    <!-- ── Left Brand Panel ────────────────────────────────────── -->
    <div class="auth-panel-left">
        <div class="auth-brand">
            <div class="auth-brand-icon"><i class="fa-solid fa-bicycle"></i></div>
            <div>
                <div class="auth-brand-name">CycleSync</div>
                <div class="auth-brand-tagline">Eco Bicycle Sharing</div>
            </div>
        </div>

        <div class="auth-panel-hero">
            <span class="auth-hero-emoji"><i class="fa-solid fa-shield-halved auth-hero-icon" style="font-size:4.5rem; color: #fff; margin-bottom: 24px; display: block;"></i></span>
            <h1 class="auth-hero-headline">Secure Your<br><span>Account.</span></h1>
            <p class="auth-hero-desc">
                Choose a strong password to keep your
                account safe. We recommend a mix of
                letters, numbers, and symbols.
            </p>
        </div>
    </div>

    <!-- ── Right Form Panel ────────────────────────────────────── -->
    <div class="auth-panel-right">

        <div class="auth-form-header">
            <h2>Set New Password 🔒</h2>
            <p>Enter your new password for <strong>${sessionScope.resetEmail}</strong></p>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <span class="alert-icon"><i data-lucide="alert-triangle"></i></span>
                ${errorMessage}
            </div>
        </c:if>

        <!-- Reset Password Form -->
        <form action="${pageContext.request.contextPath}/reset-password" method="post">

            <div class="form-group">
                <label class="form-label" for="newPassword">New Password <span class="required">*</span></label>
                <div class="input-wrap">
                    <span class="input-icon"><i data-lucide="lock"></i></span>
                    <input type="password"
                           id="newPassword"
                           name="newPassword"
                           class="form-control"
                           placeholder="Enter new password"
                           required>
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="confirmPassword">Confirm Password <span class="required">*</span></label>
                <div class="input-wrap">
                    <span class="input-icon"><i data-lucide="lock"></i></span>
                    <input type="password"
                           id="confirmPassword"
                           name="confirmPassword"
                           class="form-control"
                           placeholder="Confirm new password"
                           required>
                </div>
            </div>

            <button type="submit" class="btn btn-primary btn-lg" style="width:100%;">
                Reset Password →
            </button>

        </form>

        <div class="auth-switch">
            Changed your mind?
            <a href="${pageContext.request.contextPath}/login">Back to Login</a>
        </div>

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
