<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login — CycleSync</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css">
</head>
<body>

<div class="auth-page">

    <!-- ── Left Brand Panel ────────────────────────────────────── -->
    <div class="auth-panel-left">
        <div class="auth-brand">
            <div class="auth-brand-icon">🚲</div>
            <div>
                <div class="auth-brand-name">CycleSync</div>
                <div class="auth-brand-tagline">Eco Bicycle Sharing</div>
            </div>
        </div>

        <div class="auth-panel-hero">
            <span class="auth-hero-emoji">🚴</span>
            <h1 class="auth-hero-headline">Ride Green,<br><span>Ride Smart.</span></h1>
            <p class="auth-hero-desc">
                Join our university bicycle sharing initiative.
                Borrow a bike in seconds, return with ease,
                and help reduce carbon emissions on campus.
            </p>
        </div>

        <div class="auth-features">
            <div class="auth-feature-item">
                <span class="auth-feature-dot"></span>
                Mountain, Road, Electric &amp; Hybrid bikes
            </div>
            <div class="auth-feature-item">
                <span class="auth-feature-dot"></span>
                Real-time availability at every dock
            </div>
            <div class="auth-feature-item">
                <span class="auth-feature-dot"></span>
                Transparent pricing — no hidden fees
            </div>
            <div class="auth-feature-item">
                <span class="auth-feature-dot"></span>
                Full ride history &amp; fine management
            </div>
        </div>
    </div>

    <!-- ── Right Form Panel ────────────────────────────────────── -->
    <div class="auth-panel-right">

        <div class="auth-form-header">
            <h2>Welcome back 👋</h2>
            <p>Sign in to your CycleSync account to continue.</p>
        </div>

        <!-- Flash messages -->
        <c:if test="${not empty param.registered}">
            <div class="alert alert-success">
                <span class="alert-icon">✅</span>
                Account created! You can now log in.
            </div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <span class="alert-icon">⚠️</span>
                ${errorMessage}
            </div>
        </c:if>

        <!-- Login Form -->
        <form action="${pageContext.request.contextPath}/login" method="post" autocomplete="on">

            <div class="form-group">
                <label class="form-label" for="userEmail">Email Address <span class="required">*</span></label>
                <div class="input-wrap">
                    <span class="input-icon">✉️</span>
                    <input type="email"
                           id="userEmail"
                           name="userEmail"
                           class="form-control"
                           placeholder="you@university.edu"
                           value="${not empty param.userEmail ? param.userEmail : ''}"
                           required
                           autocomplete="email">
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="userPassword">Password <span class="required">*</span></label>
                <div class="input-wrap">
                    <span class="input-icon">🔒</span>
                    <input type="password"
                           id="userPassword"
                           name="userPassword"
                           class="form-control"
                           placeholder="Enter your password"
                           required
                           autocomplete="current-password">
                </div>
            </div>

            <div class="auth-options">
                <label class="form-check">
                    <input type="checkbox" name="rememberMe"> Remember me
                </label>
            </div>

            <button type="submit" class="btn btn-primary btn-lg" style="width:100%;">
                Sign In →
            </button>

        </form>

        <div class="auth-switch">
            Don't have an account?
            <a href="${pageContext.request.contextPath}/register">Create one free</a>
        </div>

    </div>
</div>

</body>
</html>
