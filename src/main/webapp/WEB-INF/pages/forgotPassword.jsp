<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password — CycleSync</title>
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
            <span class="auth-hero-emoji"><i class="fa-solid fa-key auth-hero-icon" style="font-size:4.5rem; color: #fff; margin-bottom: 24px; display: block;"></i></span>
            <h1 class="auth-hero-headline">Recover Your<br><span>Account.</span></h1>
            <p class="auth-hero-desc">
                Don't worry, it happens to the best of us.
                Enter your registered email address and we'll
                help you reset your password in no time.
            </p>
        </div>
    </div>

    <!-- ── Right Form Panel ────────────────────────────────────── -->
    <div class="auth-panel-right">

        <div class="auth-form-header">
            <h2>Forgot Password? 🔑</h2>
            <p>Enter your email to reset your password.</p>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <span class="alert-icon"><i data-lucide="alert-triangle"></i></span>
                ${errorMessage}
            </div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                <span class="alert-icon"><i data-lucide="check-circle-2"></i></span>
                ${successMessage}
            </div>
        </c:if>

        <!-- Forgot Password Form -->
        <form action="${pageContext.request.contextPath}/forgot-password" method="post">

            <div class="form-group">
                <label class="form-label" for="userEmail">Email Address <span class="required">*</span></label>
                <div class="input-wrap">
                    <span class="input-icon"><i data-lucide="mail"></i></span>
                    <input type="email"
                           id="userEmail"
                           name="userEmail"
                           class="form-control"
                           placeholder="you@university.edu"
                           required
                           autocomplete="email">
                </div>
            </div>

            <button type="submit" class="btn btn-primary btn-lg" style="width:100%;">
                Verify Email →
            </button>

        </form>

        <div class="auth-switch">
            Remember your password?
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
